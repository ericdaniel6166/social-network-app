package com.example.socialnetworkapp.service;

import com.example.socialnetworkapp.dto.ForumDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.model.Forum;

public interface ForumService {

    SimpleResponseDTO createForum(ForumDTO forumDTO) throws SocialNetworkAppException;

    Forum saveAndFlush(Forum forum);

}
