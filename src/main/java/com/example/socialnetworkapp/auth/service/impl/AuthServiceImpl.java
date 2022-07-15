package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.auth.dto.SignInRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignInResponseDTO;
import com.example.socialnetworkapp.auth.dto.SignUpRequestDTO;
import com.example.socialnetworkapp.auth.enums.RoleEnum;
import com.example.socialnetworkapp.auth.model.AppRole;
import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.model.VerificationToken;
import com.example.socialnetworkapp.auth.service.AuthService;
import com.example.socialnetworkapp.auth.service.EncryptionService;
import com.example.socialnetworkapp.auth.service.JwtService;
import com.example.socialnetworkapp.auth.service.RefreshTokenService;
import com.example.socialnetworkapp.auth.service.RoleService;
import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.auth.service.VerificationTokenService;
import com.example.socialnetworkapp.configuration.AppConfiguration;
import com.example.socialnetworkapp.configuration.security.JwtConfiguration;
import com.example.socialnetworkapp.dto.EmailDTO;
import com.example.socialnetworkapp.dto.ErrorDetail;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.dto.ValidationErrorDetail;
import com.example.socialnetworkapp.enums.MasterErrorCode;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.exception.ValidationException;
import com.example.socialnetworkapp.model.MasterErrorMessage;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.service.MailService;
import com.example.socialnetworkapp.service.MasterErrorMessageService;
import com.example.socialnetworkapp.service.MasterMessageService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;

    private final UserService userService;

    private final VerificationTokenService verificationTokenService;

    private final MailService mailService;

    private final EncryptionService encryptionService;

    private final MasterErrorMessageService masterErrorMessageService;

    private final MasterMessageService masterMessageService;

    private final RoleService roleService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final JwtConfiguration jwtConfiguration;

    private final RefreshTokenService refreshTokenService;

    private final AppConfiguration appConfiguration;


    @Override
    public SimpleResponseDTO verifyAccount(String token) throws SocialNetworkAppException {
        log.debug("Verify account, token: {}", token);
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        AppUser appUser = verificationToken.getAppUser();
        if (appUser.getIsActive()) {
            log.error("Account has been already activated, username: {}", appUser.getUsername());
            MasterErrorMessage masterErrorMessage = masterErrorMessageService.findByErrorCode(MasterErrorCode.ACCOUNT_ALREADY_ACTIVATED_ERROR);
            throw new SocialNetworkAppException(HttpStatus.BAD_REQUEST, MasterErrorCode.ACCOUNT_ALREADY_ACTIVATED_ERROR.name(), StringEscapeUtils.unescapeJava(masterErrorMessage.getErrorMessage()), null);
        }
        appUser.setIsActive(true);
        appUser = userService.saveAndFlush(appUser);
        SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
        MasterMessage masterMessage = masterMessageService.findByMessageCode(MasterMessageCode.VERIFY_ACCOUNT_SUCCESS);
        simpleResponseDTO.setTitle(StringEscapeUtils.unescapeJava(masterMessage.getTitle()));
        String message = CommonUtils.formatString(StringEscapeUtils.unescapeJava(masterMessage.getMessage()), appUser.getUsername());
        simpleResponseDTO.setMessage(StringEscapeUtils.unescapeJava(message));
        return simpleResponseDTO;
    }

    @Override
    public SignInResponseDTO signIn(SignInRequestDTO signInRequestDTO) throws SocialNetworkAppException {
        log.debug("Sign in, username: {}", signInRequestDTO.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDTO.getUsername()
                , signInRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String expiresAt = DateTimeFormatter.ofPattern(appConfiguration.getZonedDateTimeFormat())
                .withZone(TimeZone.getTimeZone(appConfiguration.getTimeZoneId()).toZoneId())
                .format(Instant.now().plusMillis(jwtConfiguration.getJwtExpirationInMillis()));
        return new SignInResponseDTO(jwtService.generateToken(authentication),
                refreshTokenService.generateRefreshToken().getToken(), expiresAt);
    }

    @Transactional
    @Override
    public SimpleResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) throws SocialNetworkAppException {
        log.debug("Sign up, username: {}", signUpRequestDTO.getUsername());
        validateAccountNotExists(signUpRequestDTO);

        String encryptedPassword = encryptionService.encrypt(signUpRequestDTO.getPassword());

        signUpRequestDTO.setPassword(encryptedPassword);
        AppUser appUser = modelMapper.map(signUpRequestDTO, AppUser.class);
        appUser.setIsActive(false);
        AppRole appRole = roleService.findByRoleName(RoleEnum.ROLE_USER);
        appUser.setAppRole(appRole);
        appUser = userService.saveAndFlush(appUser);
        String token = generateVerificationToken(appUser);
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setSubject(Constants.VERIFICATION_EMAIL_SUBJECT);
        emailDTO.setBody(Constants.VERIFICATION_URL + token);
        emailDTO.setRecipient(appUser.getEmail());
        mailService.sendMail(emailDTO);
        SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
        String maskEmail = CommonUtils.maskEmail(appUser.getEmail());
        MasterMessage masterMessage = masterMessageService.findByMessageCode(MasterMessageCode.SIGN_UP_SUCCESS);
        simpleResponseDTO.setTitle(StringEscapeUtils.unescapeJava(masterMessage.getTitle()));
        String message = CommonUtils.formatString(StringEscapeUtils.unescapeJava(masterMessage.getMessage()), appUser.getUsername(), maskEmail);
        simpleResponseDTO.setMessage(StringEscapeUtils.unescapeJava(message));
        return simpleResponseDTO;
    }

    private void validateAccountNotExists(SignUpRequestDTO signUpRequestDTO) throws ResourceNotFoundException, ValidationException {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        errorDetails.add(validateEmail(signUpRequestDTO.getEmail()));
        errorDetails.add(validateUsername(signUpRequestDTO.getUsername()));
        CollectionUtils.filter(errorDetails, PredicateUtils.notNullPredicate());
        if (!errorDetails.isEmpty()) {
            throw new ValidationException(HttpStatus.CONFLICT, null, errorDetails);
        }
    }

    private ErrorDetail validateEmail(String email) throws ResourceNotFoundException {
        boolean existsByEmail = userService.existsByEmail(email);
        log.debug("Validate email, email exists: {}", existsByEmail);
        if (existsByEmail) {
            MasterErrorMessage masterErrorMessage = masterErrorMessageService.findByErrorCode(MasterErrorCode.EMAIL_EXISTED_ERROR);
            return new ValidationErrorDetail(null, Constants.EMAIL.toLowerCase(), CommonUtils.maskEmail(email), StringEscapeUtils.unescapeJava(masterErrorMessage.getErrorMessage()));
        }
        return null;
    }

    private ErrorDetail validateUsername(String username) throws ResourceNotFoundException {
        boolean existsByUsername = userService.existsByUsername(username);
        log.debug("Validate username, username exists: {}", existsByUsername);
        if (existsByUsername) {
            MasterErrorMessage masterErrorMessage = masterErrorMessageService.findByErrorCode(MasterErrorCode.USERNAME_EXISTED_ERROR);
            return new ValidationErrorDetail(null, Constants.USERNAME.toLowerCase(), username, StringEscapeUtils.unescapeJava(masterErrorMessage.getErrorMessage()));
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
