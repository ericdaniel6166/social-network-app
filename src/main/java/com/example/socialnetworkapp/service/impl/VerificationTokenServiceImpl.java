package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.VerificationToken;
import com.example.socialnetworkapp.repository.VerificationTokenRepository;
import com.example.socialnetworkapp.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationToken findByToken(String token) throws ResourceNotFoundException {
        log.info("Start find verification token by token, token: {}", token);
        return verificationTokenRepository.findByToken(token).orElseThrow(
                () -> new ResourceNotFoundException("Verification token " + token));
    }

    @Override
    public VerificationToken saveAndFlush(VerificationToken verificationToken) {
        return verificationTokenRepository.saveAndFlush(verificationToken);
    }
}
