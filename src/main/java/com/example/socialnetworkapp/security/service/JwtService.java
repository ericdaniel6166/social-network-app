package com.example.socialnetworkapp.security.service;


import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateToken(Authentication authentication);

}
