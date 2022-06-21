package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.service.EncryptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String encrypt(String stringInput) {
        String stringOutput = null;
        try {
            stringOutput = passwordEncoder.encode(stringInput);
        } catch (Exception e) {
            log.error("Error encrypting, error message: {}", e.getMessage(), e);
        }
        return stringOutput;
    }

}
