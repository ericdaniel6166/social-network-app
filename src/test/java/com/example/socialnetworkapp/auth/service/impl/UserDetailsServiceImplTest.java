package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.enums.AppRoleName;
import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

class UserDetailsServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenLoadUserByUsername_thenReturnUserDetails() throws ResourceNotFoundException {
        AppUser appUser = AuthTestUtils.buildAppUser(AppRoleName.ROLE_USER, AppRoleName.ROLE_ADMIN);
        Mockito.when(userService.findByUsername(appUser.getUsername())).thenReturn(appUser);
        User expected = new User(appUser.getUsername(), appUser.getPassword(), appUser.getIsActive(),
                true, true, true, AuthTestUtils.buildAuthorityList(AppRoleName.ROLE_USER, AppRoleName.ROLE_ADMIN));
        UserDetails actual = userDetailsService.loadUserByUsername(appUser.getUsername());

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenLoadUserByUsername_thenReturnUserDetails2() throws ResourceNotFoundException {
        AppUser appUser = AuthTestUtils.buildAppUser(AppRoleName.ROLE_USER, AppRoleName.ROLE_ADMIN);
        Mockito.when(userService.findByUsername(appUser.getUsername())).thenThrow(ResourceNotFoundException.class);
        try {
            userDetailsService.loadUserByUsername(appUser.getUsername());
        } catch (Exception e) {
            Assertions.assertInstanceOf(SocialNetworkAppException.class, e);
        }

    }


}