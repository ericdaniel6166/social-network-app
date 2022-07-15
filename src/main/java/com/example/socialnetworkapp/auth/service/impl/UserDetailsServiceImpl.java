package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        AppUser appUser = userService.findByUsername(username);
        Collection<SimpleGrantedAuthority> authorityList = Collections.singletonList(
                new SimpleGrantedAuthority(appUser.getAppRole().getRoleName().name()));
        return new User(appUser.getUsername(), appUser.getPassword(),
                appUser.getIsActive(), true, true,
                true, authorityList);
    }

}
