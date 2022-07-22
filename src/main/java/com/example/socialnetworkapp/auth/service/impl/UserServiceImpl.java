package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.repository.UserRepository;
import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public AppUser saveAndFlush(AppUser appUser) {
        return userRepository.saveAndFlush(appUser);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public AppUser findByUsername(String username) throws ResourceNotFoundException {
        log.debug("Find app user by username, username: {}", username);
        return userRepository.findByIsActiveTrueAndUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("username " + username));
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser getCurrentUser() throws ResourceNotFoundException {
        return this.findByUsername(CommonUtils.getCurrentUsername());

    }
}
