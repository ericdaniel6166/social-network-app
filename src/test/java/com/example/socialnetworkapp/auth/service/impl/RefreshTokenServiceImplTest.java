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
    void whenFindByTokenAndUsername_ThenReturnRefreshToken() throws ResourceNotFoundException {
        RefreshToken refreshToken = AuthTestUtils.buildRefreshToken();
        Optional<RefreshToken> expected = Optional.of(refreshToken);
        String token = refreshToken.getToken();
        String username = refreshToken.getUsername();
        Mockito.when(refreshTokenRepository.findByTokenAndUsername(token, username)).thenReturn(expected);

        Optional<RefreshToken> actual = refreshTokenService.findByTokenAndUsername(token, username);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenDeleteByToken_thenReturnSuccess() {
        refreshTokenService.deleteByToken(AuthTestUtils.TOKEN);
    }

    @Test
    void whenGenerateRefreshToken_thenReturnRefreshToken() {
        RefreshToken expected = AuthTestUtils.buildRefreshToken();
        Mockito.when(refreshTokenRepository.saveAndFlush(Mockito.any(RefreshToken.class))).thenReturn(expected);

        RefreshToken actual = refreshTokenService.generateRefreshToken(expected.getUsername());

        Assertions.assertEquals(expected, actual);
    }
}