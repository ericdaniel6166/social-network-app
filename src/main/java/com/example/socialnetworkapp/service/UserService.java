package com.example.socialnetworkapp.service;


import com.example.socialnetworkapp.model.AppUser;

public interface UserService {

    AppUser saveAndFlush(AppUser appUser);

}
