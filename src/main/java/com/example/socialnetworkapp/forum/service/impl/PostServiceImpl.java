package com.example.socialnetworkapp.forum.service.impl;

import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.dto.PostDTO;
import com.example.socialnetworkapp.forum.model.AppComment;
import com.example.socialnetworkapp.forum.model.Post;
import com.example.socialnetworkapp.forum.repository.PostRepository;
import com.example.socialnetworkapp.forum.service.CommentService;
import com.example.socialnetworkapp.forum.service.ForumService;
import com.example.socialnetworkapp.forum.service.PostService;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.service.MasterMessageService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private CommentService commentService;

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    private final UserService userService;

    @Autowired
    private ForumService forumService;

    private final MasterMessageService masterMessageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponseDTO create(PostDTO postDTO) throws SocialNetworkAppException {
        log.debug("Create post, post name: {}", postDTO.getName());
        Post post = modelMapper.map(postDTO, Post.class);
        post.setAppUser(userService.getCurrentUser());
        post.setForum(forumService.findById(postDTO.getForumId()));
        post.setIsActive(true);
        this.saveAndFlush(post);
        MasterMessage masterMessage = masterMessageService.findByMessageCode(MasterMessageCode.CREATE_SUCCESS);
        SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
        simpleResponseDTO.setTitle(CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getTitle()),
                Constants.POST.toUpperCase()
        ));
        simpleResponseDTO.setMessage(CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getMessage()),
                Constants.POST.toLowerCase(),
                post.getName()
        ));
        return simpleResponseDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponseDTO deleteById(Long id) throws SocialNetworkAppException {
        log.debug("Delete post by id, id: {}", id);
        Post post = this.setIsActive(id, false);
        List<AppComment> commentList = post.getCommentList();
        commentService.setIsActiveList(commentList, false);
        MasterMessage masterMessage = masterMessageService.findByMessageCode(MasterMessageCode.DELETE_SUCCESS);
        SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
        simpleResponseDTO.setTitle(CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getTitle()),
                Constants.POST.toUpperCase()
        ));
        simpleResponseDTO.setMessage(CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getMessage()),
                Constants.POST.toLowerCase(),
                post.getName()
        ));
        return simpleResponseDTO;
    }

    @Override
    public Post setIsActive(Long id, boolean isActive) throws SocialNetworkAppException {
        Post post = this.findById(id);
        post.setIsActive(isActive);
        return this.saveAndFlush(post);

    }

    @Override
    public List<Post> setIsActiveList(List<Post> postList, boolean isActive) {
        postList.forEach(appComment -> appComment.setIsActive(isActive));
        return this.saveAllAndFlush(postList);
    }

    @Override
    public List<Post> saveAllAndFlush(List<Post> postList) {
        return postRepository.saveAllAndFlush(postList);
    }

    @Override
    public Post saveAndFlush(Post post) {
        return postRepository.saveAndFlush(post);
    }

    @Override
    public boolean existsById(Long id) {
        return postRepository.existsById(id);
    }

    @Override
    public Page<PostDTO> getAll(Pageable pageable, String search) throws SocialNetworkAppException {
        Page<Post> postPage;
        if (StringUtils.isBlank(search)) {
            postPage = postRepository.findAllByIsActiveTrue(pageable);
        } else {
            Specification<Post> spec = (Specification<Post>) CommonUtils.buildSpecification(search);
            postPage = postRepository.findAll(spec, pageable);
        }
        return buildPostDTOPage(postPage, pageable);
    }

    private Page<PostDTO> buildPostDTOPage(Page<Post> postPage, Pageable pageable) {
        PostDTO postDTO;
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postPage) {
            postDTO = modelMapper.map(post, PostDTO.class);
            postDTO.setForumId(post.getForum().getId());
            postDTOList.add(postDTO);
        }
        return new PageImpl<>(postDTOList, pageable, postPage.getTotalElements());
    }

    @Override
    public Post findById(Long id) throws ResourceNotFoundException {
        log.debug("Find post by id, id: {}", id);
        return postRepository.findByIsActiveTrueAndId(id).orElseThrow(
                () -> new ResourceNotFoundException(Constants.POST + ", id:" + id));
    }

    @Override
    public PostDTO getById(Long id) throws ResourceNotFoundException {
        return modelMapper.map(this.findById(id), PostDTO.class);
    }

    @Override
    public Page<PostDTO> getByCreatedBy(String username, Pageable pageable) throws ResourceNotFoundException {
        log.debug("Find post created by username, username: {}", username);
        if (!userService.existsByUsername(username)){
            throw new ResourceNotFoundException("username " + username);
        }
        Page<Post> postPage = postRepository.findAllByIsActiveTrueAndCreatedBy(username, pageable);
        return buildPostDTOPage(postPage, pageable);
    }

    @Override
    public Page<PostDTO> getByForumId(Long id, Pageable pageable) throws ResourceNotFoundException {
        log.debug("Find post by forum id, id: {}", id);
        if (!forumService.existsById(id)){
            throw new ResourceNotFoundException(Constants.FORUM + ", id:" + id);
        }
        Page<Post> postPage = postRepository.findAllByIsActiveTrueAndForum_Id(id, pageable);
        return buildPostDTOPage(postPage, pageable);
    }
}
