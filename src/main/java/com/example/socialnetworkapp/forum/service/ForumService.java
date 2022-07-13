package com.example.socialnetworkapp.forum.service;

import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.dto.ForumDTO;
import com.example.socialnetworkapp.forum.model.Forum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ForumService {

    SimpleResponseDTO create(ForumDTO forumDTO) throws SocialNetworkAppException;

    Forum saveAndFlush(Forum forum);

    boolean existsById(Long id);

    Page<ForumDTO> getAll(Pageable pageable, String search) throws SocialNetworkAppException;

    Forum findById(Long id) throws ResourceNotFoundException;

    ForumDTO getById(Long id) throws ResourceNotFoundException;
}
