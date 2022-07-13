package com.example.socialnetworkapp.forum.service;

import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.dto.PostDTO;
import com.example.socialnetworkapp.forum.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    SimpleResponseDTO create(PostDTO postDTO) throws SocialNetworkAppException;

    Post saveAndFlush(Post post);

    boolean existsById(Long id);

    Page<PostDTO> getAll(Pageable pageable, String search) throws SocialNetworkAppException;

    Post findById(Long id) throws ResourceNotFoundException;

    PostDTO getById(Long id) throws ResourceNotFoundException;

    Page<PostDTO> getByForumId(Long id, Pageable pageable) throws ResourceNotFoundException;

    Page<PostDTO> getByCreatedBy(String username, Pageable pageable) throws ResourceNotFoundException;

    SimpleResponseDTO deleteById(Long id) throws SocialNetworkAppException;
}
