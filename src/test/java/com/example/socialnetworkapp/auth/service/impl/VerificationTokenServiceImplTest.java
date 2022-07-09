package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.model.VerificationToken;
import com.example.socialnetworkapp.auth.repository.VerificationTokenRepository;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

class VerificationTokenServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private VerificationTokenServiceImpl verificationTokenService;

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenFindByToken_givenEmptyVerificationToken_thenThrowResourceNotFoundException() {
        String token = AuthTestUtils.TOKEN;
        Mockito.when(verificationTokenRepository.findByToken(token)).thenReturn(Optional.empty());
        ResourceNotFoundException expected = new ResourceNotFoundException("Verification token " + token);

        try {
            verificationTokenService.findByToken(token);
        } catch (ResourceNotFoundException e) {
            Assertions.assertEquals(expected, e);
        }
    }

    @Test
    void whenFindByToken_givenNotEmptyVerificationToken_thenReturnVerificationToken() throws ResourceNotFoundException {
        VerificationToken expected = AuthTestUtils.buildVerificationToken();
        String token = expected.getToken();
        Mockito.when(verificationTokenRepository.findByToken(token)).thenReturn(Optional.of(expected));

        VerificationToken actual = verificationTokenService.findByToken(token);

        Assertions.assertEquals(expected, actual);
    }


    @Test
    void whenSaveAndFlush_givenVerificationToken_thenReturnVerificationToken() {
        VerificationToken expected = AuthTestUtils.buildVerificationToken();
        Mockito.when(verificationTokenRepository.saveAndFlush(expected)).thenReturn(expected);

        VerificationToken actual = verificationTokenService.saveAndFlush(expected);

        Assertions.assertEquals(expected, actual);
    }
}