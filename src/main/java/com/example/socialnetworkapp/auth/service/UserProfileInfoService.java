package com.example.socialnetworkapp.auth.service;


import com.example.socialnetworkapp.auth.model.UserProfileInfo;

import java.util.Optional;

public interface UserProfileInfoService {

    UserProfileInfo saveAndFlush(UserProfileInfo userProfileInfo);

    Optional<UserProfileInfo> findByUsername(String username);

}
