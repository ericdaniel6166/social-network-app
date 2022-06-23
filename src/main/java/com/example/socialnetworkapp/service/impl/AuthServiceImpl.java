package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.dto.EmailDTO;
import com.example.socialnetworkapp.dto.RegisterRequestDTO;
import com.example.socialnetworkapp.dto.RegisterResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.model.AppUser;
import com.example.socialnetworkapp.model.VerificationToken;
import com.example.socialnetworkapp.service.AuthService;
import com.example.socialnetworkapp.service.EncryptionService;
import com.example.socialnetworkapp.service.MailService;
import com.example.socialnetworkapp.service.UserService;
import com.example.socialnetworkapp.service.VerificationTokenService;
import com.example.socialnetworkapp.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    //TODO move to master_general_parameter
    private static final String VERIFICATION_EMAIL_SUBJECT = "Please verify your email address";

    //TODO move to master_general_parameter
    private static final String VERIFICATION_URL = "http://localhost:8080/api/auth/accountVerification/";
    private static final String SIGN_UP_SUCCESS_MESSAGE = "Hi %s, we've sent an email to %s. Please click on the link given in email to verify your account.\nThe link in the email will expire in 24 hours";
    private static final String SIGN_UP_SUCCESS_TITLE = "VERIFICATION LINK SENT";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private MailService mailService;

    @Autowired
    private EncryptionService encryptionService;

    @Transactional
    @Override
    public RegisterResponseDTO signUp(RegisterRequestDTO registerRequestDTO) throws SocialNetworkAppException {
        AppUser appUser;
        String encryptedPassword = null;
        if (StringUtils.isNotBlank(registerRequestDTO.getPassword())) {
            encryptedPassword = encryptionService.encrypt(registerRequestDTO.getPassword());
        }

        registerRequestDTO.setPassword(encryptedPassword);
        appUser = modelMapper.map(registerRequestDTO, AppUser.class);
        appUser.setActive(false);
        appUser = userService.save(appUser);
        String token = generateVerificationToken(appUser);
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setSubject(VERIFICATION_EMAIL_SUBJECT);
        emailDTO.setBody(VERIFICATION_URL + token);
        emailDTO.setRecipient(appUser.getEmail());
        mailService.sendMail(emailDTO);
        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        String maskEmail = CommonUtils.maskEmail(appUser.getEmail());
        registerResponseDTO.setTitle(StringEscapeUtils.unescapeJava(SIGN_UP_SUCCESS_TITLE));
        String message = CommonUtils.formatString(SIGN_UP_SUCCESS_MESSAGE, appUser.getUsername(), maskEmail);
        registerResponseDTO.setMessage(StringEscapeUtils.unescapeJava(message));
        return registerResponseDTO;
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
