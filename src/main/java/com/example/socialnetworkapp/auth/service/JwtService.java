package com.example.socialnetworkapp.auth.service;


import org.springframework.security.core.Authentication;

import java.util.List;

public interface JwtService {

    String generateToken(Authentication authentication);

    String buildToken(String username, List<String> scope);

}
