package com.example.socialnetworkapp.auth.service;

import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.auth.model.VerificationToken;

public interface VerificationTokenService {

    VerificationToken saveAndFlush(VerificationToken verificationToken);

    VerificationToken findByToken(String token) throws ResourceNotFoundException;

}
