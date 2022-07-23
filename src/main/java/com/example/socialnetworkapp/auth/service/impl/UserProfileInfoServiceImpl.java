package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.auth.model.UserProfileInfo;
import com.example.socialnetworkapp.auth.repository.UserProfileInfoRepository;
import com.example.socialnetworkapp.auth.service.UserProfileInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileInfoServiceImpl implements UserProfileInfoService {

    private final UserProfileInfoRepository userProfileInfoRepository;

    @Override
    public UserProfileInfo saveAndFlush(UserProfileInfo userProfileInfo) {
        return userProfileInfoRepository.saveAndFlush(userProfileInfo);
    }

    @Override
    public Optional<UserProfileInfo> findByUsername(String username) {
        return userProfileInfoRepository.findByAppUser_Username(username);
    }

}
