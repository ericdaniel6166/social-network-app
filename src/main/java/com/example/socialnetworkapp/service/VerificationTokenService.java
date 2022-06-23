package com.example.socialnetworkapp.service;

import com.example.socialnetworkapp.model.VerificationToken;

public interface VerificationTokenService {

    VerificationToken saveAndFlush(VerificationToken verificationToken);
}
