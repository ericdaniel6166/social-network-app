package com.example.socialnetworkapp.forum.service;

import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.dto.CommentDTO;
import com.example.socialnetworkapp.forum.model.AppComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    void create(CommentDTO commentDTO) throws SocialNetworkAppException;

    AppComment saveAndFlush(AppComment appComment);

    List<AppComment> saveAllAndFlush(List<AppComment> commentList);

    AppComment findById(Long id) throws ResourceNotFoundException;

    Page<CommentDTO> getAll(Pageable pageable, String search) throws SocialNetworkAppException;

    Page<CommentDTO> getByPostId(Long id, Pageable pageable) throws ResourceNotFoundException;

    Page<CommentDTO> getByCreatedBy(String username, Pageable pageable) throws ResourceNotFoundException;

    CommentDTO getById(Long id) throws ResourceNotFoundException;

    List<AppComment> setIsActiveList(List<AppComment> commentList, boolean isActive);
}
