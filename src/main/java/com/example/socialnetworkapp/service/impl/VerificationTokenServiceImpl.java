package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.model.VerificationToken;
import com.example.socialnetworkapp.repository.VerificationTokenRepository;
import com.example.socialnetworkapp.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationToken saveAndFlush(VerificationToken verificationToken) {
        return verificationTokenRepository.saveAndFlush(verificationToken);
    }
}
