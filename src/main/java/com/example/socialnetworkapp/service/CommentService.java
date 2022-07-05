package com.example.socialnetworkapp.service;

import com.example.socialnetworkapp.forum.dto.CommentDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.model.AppComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    void create(CommentDTO commentDTO) throws SocialNetworkAppException;

    AppComment saveAndFlush(AppComment appComment);

    Page<CommentDTO> findAll(Pageable pageable, String search) throws SocialNetworkAppException;

}
