package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.model.RefreshToken;
import com.example.socialnetworkapp.auth.repository.RefreshTokenRepository;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

class RefreshTokenServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private RefreshTokenServiceImpl refreshTokenService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenFindByToken_givenNotEmptyRefreshToken_ThenReturnRefreshToken() throws ResourceNotFoundException {
        RefreshToken expected = AuthTestUtils.buildRefreshToken();
        String token = expected.getToken();
        Mockito.when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(expected));

        RefreshToken actual = refreshTokenService.findByToken(token);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenFindByToken_givenEmptyRefreshToken_ThenThrowResourceNotFoundException() {
        RefreshToken refreshToken = AuthTestUtils.buildRefreshToken();
        String token = refreshToken.getToken();
        Mockito.when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.empty());
        ResourceNotFoundException expected = new ResourceNotFoundException("Refresh token " + token);

        try {
            refreshTokenService.findByToken(token);
        } catch (ResourceNotFoundException e) {
            Assertions.assertEquals(expected, e);
        }


    }


    @Test
    void whenDeleteByToken_thenReturnSuccess() {
        refreshTokenService.deleteByToken(AuthTestUtils.TOKEN);
    }

    @Test
    void whenGenerateRefreshToken_thenReturnRefreshToken() {
        RefreshToken expected = AuthTestUtils.buildRefreshToken();
        Mockito.when(refreshTokenRepository.saveAndFlush(Mockito.any(RefreshToken.class))).thenReturn(expected);

        RefreshToken actual = refreshTokenService.generateRefreshToken();

        Assertions.assertEquals(expected, actual);
    }
}