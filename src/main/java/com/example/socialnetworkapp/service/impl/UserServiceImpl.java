package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.model.AppUser;
import com.example.socialnetworkapp.repository.UserRepository;
import com.example.socialnetworkapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public AppUser save(AppUser appUser) {
        return userRepository.saveAndFlush(appUser);
    }
}
