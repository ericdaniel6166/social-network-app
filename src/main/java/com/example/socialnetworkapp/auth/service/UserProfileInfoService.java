package com.example.socialnetworkapp.auth.service;


import com.example.socialnetworkapp.auth.model.UserProfileInfo;

public interface UserProfileInfoService {

    UserProfileInfo saveAndFlush(UserProfileInfo userProfileInfo);

}
