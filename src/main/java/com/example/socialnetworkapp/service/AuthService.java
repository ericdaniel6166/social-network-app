package com.example.socialnetworkapp.service;

import com.example.socialnetworkapp.dto.RegisterRequestDTO;
import com.example.socialnetworkapp.dto.RegisterResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;

public interface AuthService {

    RegisterResponseDTO signUp(RegisterRequestDTO registerRequestDTO) throws SocialNetworkAppException;

}
