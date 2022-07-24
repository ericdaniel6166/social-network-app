package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.enums.RoleEnum;
import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.repository.UserRepository;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

class UserServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private Jwt jwt;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenSaveAndFlush_givenAppUser_thenReturnAppUser() {
        AppUser expected = AuthTestUtils.buildAppUser(RoleEnum.ROLE_USER);
        Mockito.when(userRepository.saveAndFlush(expected)).thenReturn(expected);

        AppUser actual = userService.saveAndFlush(expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenExistsByEmail_givenEmail_thenReturnExistsByEmail() {
        String email = AuthTestUtils.EMAIL;
        Mockito.when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean actual = userService.existsByEmail(email);

        Assertions.assertTrue(actual);
    }

    @Test
    void whenExistsByUsername_givenUsername_thenReturnExistsByUsername() {
        String username = AuthTestUtils.USERNAME;
        Mockito.when(userRepository.existsByUsername(username)).thenReturn(true);

        boolean actual = userService.existsByUsername(username);

        Assertions.assertTrue(actual);
    }

    @Test
    void whenFindByUsername_givenNotEmptyAppUser_thenReturnAppUser() throws ResourceNotFoundException {
        AppUser expected = AuthTestUtils.buildAppUser(RoleEnum.ROLE_USER);
        String username = expected.getUsername();
        Mockito.when(userRepository.findByIsActiveTrueAndUsername(username)).thenReturn(Optional.of(expected));

        AppUser actual = userService.findByUsername(username);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenFindByUsername_givenEmptyAppUser_thenThrowResourceNotFoundException() {
        AppUser appUser = AuthTestUtils.buildAppUser(RoleEnum.ROLE_USER);
        String username = appUser.getUsername();
        Mockito.when(userRepository.findByIsActiveTrueAndUsername(username)).thenReturn(Optional.empty());
        ResourceNotFoundException expected = new ResourceNotFoundException("username " + username);

        try {
            userService.findByUsername(username);
        } catch (ResourceNotFoundException e) {
            Assertions.assertEquals(expected, e);
        }

    }
}