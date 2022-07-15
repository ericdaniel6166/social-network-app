package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.dto.SignInRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignInResponseDTO;
import com.example.socialnetworkapp.auth.dto.SignUpRequestDTO;
import com.example.socialnetworkapp.auth.enums.RoleEnum;
import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.model.VerificationToken;
import com.example.socialnetworkapp.auth.service.RefreshTokenService;
import com.example.socialnetworkapp.auth.service.RoleService;
import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.auth.service.VerificationTokenService;
import com.example.socialnetworkapp.configuration.AppConfiguration;
import com.example.socialnetworkapp.configuration.security.JwtConfiguration;
import com.example.socialnetworkapp.dto.ErrorDetail;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.dto.ValidationErrorDetail;
import com.example.socialnetworkapp.enums.MasterErrorCode;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.exception.ValidationException;
import com.example.socialnetworkapp.model.MasterErrorMessage;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.auth.service.EncryptionService;
import com.example.socialnetworkapp.auth.service.JwtService;
import com.example.socialnetworkapp.service.MailService;
import com.example.socialnetworkapp.service.MasterErrorMessageService;
import com.example.socialnetworkapp.service.MasterMessageService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

class AuthServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private VerificationTokenService verificationTokenService;

    @Mock
    private UserService userService;

    @Mock
    private MailService mailService;

    @Mock
    private EncryptionService encryptionService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RoleService roleService;

    @Mock
    private MasterMessageService masterMessageService;

    @Mock
    private MasterErrorMessageService masterErrorMessageService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AppConfiguration appConfiguration;

    @Mock
    private JwtConfiguration jwtConfiguration;

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenVerifyAccount_givenValidToken_thenReturnSimpleResponseDTO() throws SocialNetworkAppException {
        String token = AuthTestUtils.TOKEN;
        VerificationToken verificationToken = AuthTestUtils.buildVerificationToken();
        AppUser appUser = verificationToken.getAppUser();
        appUser.setIsActive(false);
        Mockito.when(verificationTokenService.findByToken(token)).thenReturn(verificationToken);
        Mockito.when(userService.saveAndFlush(appUser)).thenReturn(appUser);
        MasterMessage masterMessage = new MasterMessage();
        masterMessage.setMessage(AuthTestUtils.VERIFY_ACCOUNT_SUCCESS_MESSAGE);
        masterMessage.setMessageCode(MasterMessageCode.VERIFY_ACCOUNT_SUCCESS);
        masterMessage.setTitle(AuthTestUtils.VERIFY_ACCOUNT_SUCCESS_TITLE);
        Mockito.when(masterMessageService.findByMessageCode(MasterMessageCode.VERIFY_ACCOUNT_SUCCESS)).thenReturn(masterMessage);
        String title = StringEscapeUtils.unescapeJava(masterMessage.getTitle());
        String message = CommonUtils.formatString(StringEscapeUtils.unescapeJava(masterMessage.getMessage()), appUser.getUsername());
        SimpleResponseDTO expected = new SimpleResponseDTO(title, message);

        SimpleResponseDTO actual = authService.verifyAccount(token);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenVerifyAccount_givenActiveUser_thenThrowSocialNetworkAppException() throws SocialNetworkAppException {
        String token = AuthTestUtils.TOKEN;
        VerificationToken verificationToken = AuthTestUtils.buildVerificationToken();
        AppUser appUser = verificationToken.getAppUser();
        appUser.setIsActive(true);
        Mockito.when(verificationTokenService.findByToken(token)).thenReturn(verificationToken);
        MasterErrorMessage masterErrorMessage = CommonTestUtils.buildMasterErrorMessage(MasterErrorCode.ACCOUNT_ALREADY_ACTIVATED_ERROR);
        Mockito.when(masterErrorMessageService.findByErrorCode(MasterErrorCode.ACCOUNT_ALREADY_ACTIVATED_ERROR)).thenReturn(masterErrorMessage);

        try {
            authService.verifyAccount(token);
        } catch (SocialNetworkAppException e) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
            Assertions.assertEquals(MasterErrorCode.ACCOUNT_ALREADY_ACTIVATED_ERROR.name(), e.getError());
            Assertions.assertEquals(masterErrorMessage.getErrorMessage(), e.getMessage());
        }

    }


    @Test
    void whenSignIn_givenValidSignInRequestDTO_thenReturnSignInResponseDTO() throws SocialNetworkAppException {
        SignInRequestDTO signInRequestDTO = AuthTestUtils.buildSignInRequestDTO();
        Authentication authentication = new UsernamePasswordAuthenticationToken(signInRequestDTO.getUsername()
                , signInRequestDTO.getPassword());
        Mockito.when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
        Mockito.when(appConfiguration.getTimeZoneId()).thenReturn(CommonTestUtils.TIME_ZONE_ID);
        Mockito.when(appConfiguration.getZonedDateTimeFormat()).thenReturn(CommonTestUtils.ZONED_DATE_TIME_FORMAT);
        Mockito.when(jwtConfiguration.getJwtExpirationInMillis()).thenReturn(AuthTestUtils.JWT_EXPIRATION_IN_MILLIS);
        Mockito.when(jwtService.generateToken(authentication)).thenReturn(AuthTestUtils.TOKEN);
        Mockito.when(refreshTokenService.generateRefreshToken()).thenReturn(AuthTestUtils.buildRefreshToken());

        SignInResponseDTO actual = authService.signIn(signInRequestDTO);

        Assertions.assertEquals(AuthTestUtils.TOKEN, actual.getAccessToken());
        Assertions.assertEquals(AuthTestUtils.TOKEN, actual.getRefreshToken());
        Assertions.assertTrue(StringUtils.isNotBlank(actual.getExpiresAt()));
    }

    @Test
    void whenSignUp_givenValidSignUpRequestDTO_thenReturnSimpleResponseDTO() throws SocialNetworkAppException {
        SignUpRequestDTO signUpRequestDTO = AuthTestUtils.buildSignUpRequestDTO();
        Mockito.when(userService.existsByEmail(signUpRequestDTO.getEmail())).thenReturn(false);
        Mockito.when(userService.existsByUsername(signUpRequestDTO.getUsername())).thenReturn(false);
        String encryptedPassword = AuthTestUtils.ENCRYPTED_PASSWORD;
        Mockito.when(encryptionService.encrypt(signUpRequestDTO.getPassword())).thenReturn(encryptedPassword);
        AppUser appUser = AuthTestUtils.buildAppUser(RoleEnum.ROLE_USER);
        Mockito.when(modelMapper.map(signUpRequestDTO, AppUser.class)).thenReturn(appUser);
        Mockito.when(roleService.findByRoleName(RoleEnum.ROLE_USER)).thenReturn(AuthTestUtils.buildAppRole(RoleEnum.ROLE_USER));
        Mockito.when(userService.saveAndFlush(appUser)).thenReturn(appUser);
        Mockito.when(verificationTokenService.saveAndFlush(Mockito.any())).thenReturn(AuthTestUtils.buildVerificationToken());
        MasterMessage masterMessage = new MasterMessage();
        masterMessage.setMessageCode(MasterMessageCode.SIGN_UP_SUCCESS);
        masterMessage.setMessage(AuthTestUtils.SIGN_UP_SUCCESS_MESSAGE);

        Mockito.when(masterMessageService.findByMessageCode(MasterMessageCode.SIGN_UP_SUCCESS)).thenReturn(masterMessage);
        String maskEmail = CommonUtils.maskEmail(appUser.getEmail());
        String message = CommonUtils.formatString(StringEscapeUtils.unescapeJava(masterMessage.getMessage()), appUser.getUsername(), maskEmail);
        masterMessage.setTitle(AuthTestUtils.SIGN_UP_SUCCESS_TITLE);
        String title = StringEscapeUtils.unescapeJava(masterMessage.getTitle());
        SimpleResponseDTO expected = new SimpleResponseDTO(title, message);


        SimpleResponseDTO actual = authService.signUp(signUpRequestDTO);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenSignUp_givenUsernameExisted_thenThrowValidationException() throws SocialNetworkAppException {
        SignUpRequestDTO signUpRequestDTO = AuthTestUtils.buildSignUpRequestDTO();
        Mockito.when(userService.existsByEmail(signUpRequestDTO.getEmail())).thenReturn(false);
        Mockito.when(userService.existsByUsername(signUpRequestDTO.getUsername())).thenReturn(true);
        MasterErrorMessage masterErrorMessage = CommonTestUtils.buildMasterErrorMessage(MasterErrorCode.USERNAME_EXISTED_ERROR);
        Mockito.when(masterErrorMessageService.findByErrorCode(MasterErrorCode.USERNAME_EXISTED_ERROR)).thenReturn(masterErrorMessage);
        List<ErrorDetail> errorDetails = new ArrayList<>();
        errorDetails.add(new ValidationErrorDetail(null, Constants.USERNAME.toLowerCase(), CommonUtils.maskEmail(signUpRequestDTO.getEmail()), StringEscapeUtils.unescapeJava(masterErrorMessage.getErrorMessage())));
        ValidationException expected = new ValidationException(HttpStatus.CONFLICT, null, errorDetails);

        try {
            authService.signUp(signUpRequestDTO);
        } catch (ValidationException e) {
            Assertions.assertEquals(expected, e);
        }

    }


    @Test
    void whenSignUp_givenEmailExisted_thenThrowValidationException() throws SocialNetworkAppException {
        SignUpRequestDTO signUpRequestDTO = AuthTestUtils.buildSignUpRequestDTO();
        Mockito.when(userService.existsByEmail(signUpRequestDTO.getEmail())).thenReturn(true);
        MasterErrorMessage masterErrorMessage = CommonTestUtils.buildMasterErrorMessage(MasterErrorCode.EMAIL_EXISTED_ERROR);
        Mockito.when(masterErrorMessageService.findByErrorCode(MasterErrorCode.EMAIL_EXISTED_ERROR)).thenReturn(masterErrorMessage);
        List<ErrorDetail> errorDetails = new ArrayList<>();
        errorDetails.add(new ValidationErrorDetail(null, Constants.EMAIL.toLowerCase(), CommonUtils.maskEmail(signUpRequestDTO.getEmail()), StringEscapeUtils.unescapeJava(masterErrorMessage.getErrorMessage())));
        ValidationException expected = new ValidationException(HttpStatus.CONFLICT, null, errorDetails);

        try {
            authService.signUp(signUpRequestDTO);
        } catch (ValidationException e) {
            Assertions.assertEquals(expected, e);
        }

    }


}