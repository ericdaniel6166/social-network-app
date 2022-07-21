package com.example.socialnetworkapp.auth.service;


import com.example.socialnetworkapp.auth.dto.UserRoleUpdateDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;

public interface AccountService {

    SimpleResponseDTO updateRole(UserRoleUpdateDTO userRoleUpdateDTO) throws SocialNetworkAppException;
}
