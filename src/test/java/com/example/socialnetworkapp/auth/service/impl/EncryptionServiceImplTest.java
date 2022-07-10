package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.auth.service.impl.EncryptionServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

class EncryptionServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private EncryptionServiceImpl encryptionService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenEncrypt_thenReturnStringOutput() {
        String stringInput = RandomStringUtils.random(10);
        String expected = RandomStringUtils.random(10);
        Mockito.when(passwordEncoder.encode(stringInput)).thenReturn(expected);

        String actual = encryptionService.encrypt(stringInput);

        Assertions.assertEquals(expected, actual);
    }
}