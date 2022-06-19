package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.dto.EmailDTO;
import com.example.socialnetworkapp.dto.RegisterRequestDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.model.AppUser;
import com.example.socialnetworkapp.model.VerificationToken;
import com.example.socialnetworkapp.service.AuthService;
import com.example.socialnetworkapp.service.MailService;
import com.example.socialnetworkapp.service.UserService;
import com.example.socialnetworkapp.service.VerificationTokenService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    //TODO move to master_general_parameter
    private static final String VERIFICATION_EMAIL_SUBJECT = "Please verify your email address";

    //TODO move to master_general_parameter
    private static final String VERIFICATION_URL = "http://localhost:8080/api/auth/accountVerification/";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private MailService mailService;

    @Transactional
    @Override
    public void signUp(RegisterRequestDTO registerRequestDTO) throws SocialNetworkAppException {
        AppUser appUser;
        String encryptedPassword = null;
        if (StringUtils.isNotBlank(registerRequestDTO.getPassword())) {
            encryptedPassword = passwordEncoder.encode(registerRequestDTO.getPassword());
        }

        registerRequestDTO.setPassword(encryptedPassword);
        appUser = modelMapper.map(registerRequestDTO, AppUser.class);
        appUser.setActive(false);
        userService.save(appUser);
        String token = generateVerificationToken(appUser);
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setSubject(VERIFICATION_EMAIL_SUBJECT);
        emailDTO.setBody(VERIFICATION_URL + token);
        emailDTO.setRecipient(appUser.getEmail());
        mailService.sendMail(emailDTO);
    }

    private String generateVerificationToken(AppUser appUser) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setAppUser(appUser);
        verificationTokenService.save(verificationToken);
        return token;
    }
}
