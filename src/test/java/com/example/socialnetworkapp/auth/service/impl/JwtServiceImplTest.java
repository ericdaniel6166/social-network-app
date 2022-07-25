package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.enums.RoleEnum;
import com.example.socialnetworkapp.configuration.security.JwtConfiguration;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.util.ArrayList;
import java.util.Collection;

class JwtServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private JwtConfiguration jwtConfiguration;

    @Mock
    private User user;

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
    void whenGenerateToken_thenReturnToken() {
        String expected = RandomStringUtils.random(10);
        Collection<GrantedAuthority> authorityList = new ArrayList<>(AuthTestUtils.buildAuthorityList(RoleEnum.ROLE_USER));
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(user.getAuthorities()).thenReturn(authorityList);
        Mockito.when(user.getUsername()).thenReturn(AuthTestUtils.USERNAME);
        Mockito.when(jwtConfiguration.getJwtExpirationInMillis()).thenReturn(AuthTestUtils.JWT_EXPIRATION_IN_MILLIS);
        Mockito.when(jwtEncoder.encode(Mockito.any(JwtEncoderParameters.class))).thenReturn(jwt);
        Mockito.when(jwt.getTokenValue()).thenReturn(expected);

        String actual = jwtService.generateToken(authentication);

        Assertions.assertEquals(expected, actual);
    }


}