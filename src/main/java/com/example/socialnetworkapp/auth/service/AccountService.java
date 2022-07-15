package com.example.socialnetworkapp.auth.service;


import com.example.socialnetworkapp.auth.dto.UserDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;

public interface AccountService {

    SimpleResponseDTO updateRole(UserDTO userDTO) throws SocialNetworkAppException;
}
