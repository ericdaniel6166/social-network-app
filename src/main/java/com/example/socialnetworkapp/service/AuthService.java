package com.example.socialnetworkapp.service;

import com.example.socialnetworkapp.dto.RegisterRequestDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;

public interface AuthService {

    void signUp(RegisterRequestDTO registerRequestDTO) throws SocialNetworkAppException;

}
