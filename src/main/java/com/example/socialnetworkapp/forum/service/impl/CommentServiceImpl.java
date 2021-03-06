package com.example.socialnetworkapp.forum.service.impl;

import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.dto.CommentDTO;
import com.example.socialnetworkapp.forum.model.AppComment;
import com.example.socialnetworkapp.forum.repository.CommentRepository;
import com.example.socialnetworkapp.forum.service.CommentService;
import com.example.socialnetworkapp.forum.service.PostService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final ModelMapper modelMapper;

    @Autowired
    private PostService postService;

    @Override
    public AppComment saveAndFlush(AppComment appComment) {
        return commentRepository.saveAndFlush(appComment);
    }

    @Override
    public List<AppComment> saveAllAndFlush(List<AppComment> commentList) {
        return commentRepository.saveAllAndFlush(commentList);
    }

    @Override
    public Page<CommentDTO> getAll(Pageable pageable, String search) throws SocialNetworkAppException {
        Page<AppComment> commentPage;
        if (StringUtils.isBlank(search)) {
            commentPage = commentRepository.findAllByIsActiveTrue(pageable);
        } else {
            Specification<AppComment> spec = (Specification<AppComment>) CommonUtils.buildSpecification(search);
            commentPage = commentRepository.findAll(spec, pageable);
        }
        return buildCommentDTOPage(commentPage, pageable);
    }

    private Page<CommentDTO> buildCommentDTOPage(Page<AppComment> commentPage, Pageable pageable) {
        CommentDTO commentDTO;
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (AppComment appComment : commentPage) {
            commentDTO = modelMapper.map(appComment, CommentDTO.class);
            commentDTO.setPostId(appComment.getPost().getId());
            commentDTOList.add(commentDTO);
        }

        return new PageImpl<>(commentDTOList, pageable, commentPage.getTotalElements());
    }

    @Override
    public Page<CommentDTO> getByPostId(Long id, Pageable pageable) {
        log.debug("Find comment by post id, id: {}", id);
        Page<AppComment> commentPage = commentRepository.findAllByPost_IdAndIsActiveTrue(id, pageable);
        return buildCommentDTOPage(commentPage, pageable);
    }

    @Override
    public Page<CommentDTO> getByUsername(String username, Pageable pageable) throws ResourceNotFoundException {
        log.debug("Find comment by username, username: {}", username);
        Page<AppComment> commentPage = commentRepository.findAllByUsernameAndIsActiveTrue(username, pageable);
        return buildCommentDTOPage(commentPage, pageable);
    }

    @Override
    public CommentDTO getById(Long id) throws ResourceNotFoundException {
        return modelMapper.map(this.findById(id), CommentDTO.class);
    }

    @Override
    public List<AppComment> setIsActiveList(List<AppComment> commentList, boolean isActive) {
        commentList.forEach(appComment -> appComment.setIsActive(isActive));
        return this.saveAllAndFlush(commentList);
    }

    @Override
    public AppComment setIsActive(Long id, boolean isActive) throws ResourceNotFoundException {
        AppComment appComment = this.findById(id);
        appComment.setIsActive(isActive);
        return this.saveAndFlush(appComment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) throws ResourceNotFoundException {
        this.setIsActive(id, false);
    }

    @Override
    public AppComment findById(Long id) throws ResourceNotFoundException {
        log.debug("Find comment by id, id: {}", id);
        return commentRepository.findByIdAndIsActiveTrue(id).orElseThrow(
                () -> new ResourceNotFoundException(Constants.COMMENT + ", id:" + id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(CommentDTO commentDTO) throws SocialNetworkAppException {
        AppComment appComment = modelMapper.map(commentDTO, AppComment.class);
        appComment.setUsername(CommonUtils.getCurrentUsername());
        appComment.setPost(postService.findById(commentDTO.getPostId()));
        appComment.setIsActive(true);
        this.saveAndFlush(appComment);

    }

}
