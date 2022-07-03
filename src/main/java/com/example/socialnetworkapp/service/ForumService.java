package com.example.socialnetworkapp.service;

import com.example.socialnetworkapp.dto.ForumDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.model.Forum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ForumService {

    SimpleResponseDTO create(ForumDTO forumDTO) throws SocialNetworkAppException;

    Forum saveAndFlush(Forum forum);

    Page<ForumDTO> findAll(Pageable pageable) throws SocialNetworkAppException;

    Forum findById(Long id) throws ResourceNotFoundException;
}
