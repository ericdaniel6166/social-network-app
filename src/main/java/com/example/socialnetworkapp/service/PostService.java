package com.example.socialnetworkapp.service;

import com.example.socialnetworkapp.dto.PostDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    SimpleResponseDTO create(PostDTO postDTO) throws SocialNetworkAppException;

    Post saveAndFlush(Post post);

    Page<PostDTO> findAll(Pageable pageable, String search) throws SocialNetworkAppException;

    Post findById(Long id) throws ResourceNotFoundException;
}
