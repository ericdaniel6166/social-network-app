package com.example.socialnetworkapp.auth.service;


import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;

public interface UserService {

    AppUser saveAndFlush(AppUser appUser);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    AppUser findByUsername(String username) throws ResourceNotFoundException;

}
