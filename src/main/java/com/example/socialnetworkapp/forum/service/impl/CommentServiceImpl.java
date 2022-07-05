package com.example.socialnetworkapp.forum.service.impl;

import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.dto.CommentDTO;
import com.example.socialnetworkapp.forum.model.AppComment;
import com.example.socialnetworkapp.forum.repository.CommentRepository;
import com.example.socialnetworkapp.forum.service.CommentService;
import com.example.socialnetworkapp.forum.service.PostService;
import com.example.socialnetworkapp.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
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

    private final PostService postService;

    private final CommentRepository commentRepository;

    private final ModelMapper modelMapper;

    private final UserService userService;

    @Override
    public AppComment saveAndFlush(AppComment appComment) {
        return commentRepository.saveAndFlush(appComment);
    }

    @Override
    public Page<CommentDTO> findAll(Pageable pageable, String search) throws SocialNetworkAppException {
        Page<AppComment> commentPage;
        if (StringUtils.isBlank(search)) {
            commentPage = commentRepository.findAll(pageable);
        } else {
            Specification<AppComment> spec = (Specification<AppComment>) CommonUtils.buildSpecification(search);
            commentPage = commentRepository.findAll(spec, pageable);
        }
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
    @Transactional
    public void create(CommentDTO commentDTO) throws SocialNetworkAppException {
        AppComment appComment = modelMapper.map(commentDTO, AppComment.class);
        appComment.setAppUser(userService.getCurrentUser());
        appComment.setPost(postService.findById(commentDTO.getPostId()));
        appComment.setIsActive(true);
        saveAndFlush(appComment);


    }

}
