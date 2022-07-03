package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EncryptionServiceImpl implements EncryptionService {

    private final PasswordEncoder passwordEncoder;

    public String encrypt(String stringInput) {
        return passwordEncoder.encode(stringInput);
    }

}
