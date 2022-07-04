package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.dto.PostDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.model.Post;
import com.example.socialnetworkapp.repository.PostRepository;
import com.example.socialnetworkapp.service.ForumService;
import com.example.socialnetworkapp.service.MasterMessageService;
import com.example.socialnetworkapp.service.PostService;
import com.example.socialnetworkapp.service.UserService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    private final UserService userService;

    private final ForumService forumService;

    private final MasterMessageService masterMessageService;

    @Override
    @Transactional
    public SimpleResponseDTO create(PostDTO postDTO) throws SocialNetworkAppException {
        log.debug("Create post, post name: {}", postDTO.getName());
        Post post = modelMapper.map(postDTO, Post.class);
        post.setAppUser(userService.getCurrentUser());
        post.setForum(forumService.findById(postDTO.getForumId()));
        saveAndFlush(post);
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
    public Post saveAndFlush(Post post) {
        return postRepository.saveAndFlush(post);
    }

    @Override
    public Page<PostDTO> findAll(Pageable pageable) throws SocialNetworkAppException {
        //TODO
        return null;
    }
}
