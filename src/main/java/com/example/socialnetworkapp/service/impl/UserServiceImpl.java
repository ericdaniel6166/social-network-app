package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.AppUser;
import com.example.socialnetworkapp.repository.UserRepository;
import com.example.socialnetworkapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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
        log.info("Start find app user by username, username: {}", username);
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("username " + username));
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser getCurrentUser() throws ResourceNotFoundException {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return findByUsername(principal.getSubject());
    }
}
