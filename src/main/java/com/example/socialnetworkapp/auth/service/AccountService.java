package com.example.socialnetworkapp.auth.service;


import com.example.socialnetworkapp.auth.dto.UserProfileInfoRequestDTO;
import com.example.socialnetworkapp.auth.dto.UserRoleUpdateRequestDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;

public interface AccountService {

    SimpleResponseDTO updateRole(UserRoleUpdateRequestDTO userRoleUpdateRequestDTO) throws SocialNetworkAppException;

    SimpleResponseDTO createOrUpdateProfile(String username, UserProfileInfoRequestDTO userProfileInfoRequestDTO) throws ResourceNotFoundException;
}
