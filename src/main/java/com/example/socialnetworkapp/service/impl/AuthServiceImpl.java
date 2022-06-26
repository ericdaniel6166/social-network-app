package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.dto.EmailDTO;
import com.example.socialnetworkapp.dto.ErrorDetail;
import com.example.socialnetworkapp.dto.RegisterRequestDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.dto.ValidationErrorDetail;
import com.example.socialnetworkapp.enums.MasterErrorCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.exception.ValidationException;
import com.example.socialnetworkapp.model.AppUser;
import com.example.socialnetworkapp.model.MasterErrorMessage;
import com.example.socialnetworkapp.model.VerificationToken;
import com.example.socialnetworkapp.service.AuthService;
import com.example.socialnetworkapp.service.EncryptionService;
import com.example.socialnetworkapp.service.MailService;
import com.example.socialnetworkapp.service.MasterErrorMessageService;
import com.example.socialnetworkapp.service.UserService;
import com.example.socialnetworkapp.service.VerificationTokenService;
import com.example.socialnetworkapp.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    //TODO move to master_general_parameter
    private static final String VERIFICATION_EMAIL_SUBJECT = "Please verify your email address";

    //TODO move to master_general_parameter
    private static final String VERIFICATION_URL = "http://localhost:8080/auth/verifyAccount/";
    //TODO move to master_message
    private static final String SIGN_UP_SUCCESS_MESSAGE = "Hi %s, we've sent an email to %s. Please click on the link given in email to verify your account.\nThe link in the email will expire in 24 hours.";
    private static final String SIGN_UP_SUCCESS_TITLE = "VERIFICATION LINK SENT";
    private static final String VERIFY_ACCOUNT_SUCCESS_TITLE = "VERIFICATION SUCCESS";
    private static final String VERIFY_ACCOUNT_SUCCESS_MESSAGE = "Hi %s, your account has been verified.";

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

    @Autowired
    private MasterErrorMessageService masterErrorMessageService;

    @Override
    public SimpleResponseDTO verifyAccount(String token) throws SocialNetworkAppException {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        AppUser appUser = verificationToken.getAppUser();
        if (appUser.isActive()) {
            log.error("Account has been already activated, username: {}", appUser.getUsername());
            MasterErrorMessage masterErrorMessage = masterErrorMessageService.findByErrorCode(MasterErrorCode.ACCOUNT_ALREADY_ACTIVATED_ERROR);
            throw new SocialNetworkAppException(HttpStatus.BAD_REQUEST, MasterErrorCode.ACCOUNT_ALREADY_ACTIVATED_ERROR.name(), StringEscapeUtils.unescapeJava(masterErrorMessage.getErrorMessage()), null);
        }
        appUser.setActive(true);
        userService.saveAndFlush(appUser);
        SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
        simpleResponseDTO.setTitle(VERIFY_ACCOUNT_SUCCESS_TITLE);
        simpleResponseDTO.setMessage(CommonUtils.formatString(VERIFY_ACCOUNT_SUCCESS_MESSAGE, appUser.getUsername()));
        return simpleResponseDTO;
    }

    @Transactional
    @Override
    public SimpleResponseDTO signUp(RegisterRequestDTO registerRequestDTO) throws SocialNetworkAppException {
        validateAccountNotExists(registerRequestDTO);

        String encryptedPassword = null;
        if (StringUtils.isNotBlank(registerRequestDTO.getPassword())) {
            encryptedPassword = encryptionService.encrypt(registerRequestDTO.getPassword());
        }

        registerRequestDTO.setPassword(encryptedPassword);
        AppUser appUser = modelMapper.map(registerRequestDTO, AppUser.class);
        appUser.setActive(false);
        appUser = userService.saveAndFlush(appUser);
        String token = generateVerificationToken(appUser);
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setSubject(VERIFICATION_EMAIL_SUBJECT);
        emailDTO.setBody(VERIFICATION_URL + token);
        emailDTO.setRecipient(appUser.getEmail());
        mailService.sendMail(emailDTO);
        SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
        String maskEmail = CommonUtils.maskEmail(appUser.getEmail());
        simpleResponseDTO.setTitle(StringEscapeUtils.unescapeJava(SIGN_UP_SUCCESS_TITLE));
        String message = CommonUtils.formatString(SIGN_UP_SUCCESS_MESSAGE, appUser.getUsername(), maskEmail);
        simpleResponseDTO.setMessage(StringEscapeUtils.unescapeJava(message));
        return simpleResponseDTO;
    }

    public void validateAccountNotExists(RegisterRequestDTO registerRequestDTO) throws ResourceNotFoundException, ValidationException {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        errorDetails.add(validateEmail(registerRequestDTO.getEmail()));
        errorDetails.add(validateUsername(registerRequestDTO.getUsername()));
        CollectionUtils.filter(errorDetails, PredicateUtils.notNullPredicate());
        if (!errorDetails.isEmpty()) {
            throw new ValidationException(HttpStatus.CONFLICT, null, errorDetails);
        }
    }

    private ErrorDetail validateEmail(String email) throws ResourceNotFoundException {
        boolean existsByEmail = userService.existsByEmail(email);
        log.info("Validate email, email exists: {}", existsByEmail);
        if (existsByEmail) {
            MasterErrorMessage masterErrorMessage = masterErrorMessageService.findByErrorCode(MasterErrorCode.EMAIL_EXISTS_ERROR);
            return new ValidationErrorDetail(null, "email", CommonUtils.maskEmail(email), StringEscapeUtils.unescapeJava(masterErrorMessage.getErrorMessage()));
        }
        return null;
    }

    private ErrorDetail validateUsername(String username) throws ResourceNotFoundException {
        boolean existsByUsername = userService.existsByUsername(username);
        log.info("Validate username, username exists: {}", existsByUsername);
        if (existsByUsername) {
            MasterErrorMessage masterErrorMessage = masterErrorMessageService.findByErrorCode(MasterErrorCode.USERNAME_EXISTS_ERROR);
            return new ValidationErrorDetail(null, "username", username, StringEscapeUtils.unescapeJava(masterErrorMessage.getErrorMessage()));
        }
        return null;
    }

    private String generateVerificationToken(AppUser appUser) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setAppUser(appUser);
        verificationTokenService.saveAndFlush(verificationToken);
        return token;
    }

}
