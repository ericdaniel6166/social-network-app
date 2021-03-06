package com.example.socialnetworkapp.auth;

import com.example.socialnetworkapp.auth.dto.AuthenticationResponseDTO;
import com.example.socialnetworkapp.auth.dto.RefreshTokenRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignInRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignUpRequestDTO;
import com.example.socialnetworkapp.auth.dto.UserProfileInfoDTO;
import com.example.socialnetworkapp.auth.dto.UserRoleUpdateRequestDTO;
import com.example.socialnetworkapp.auth.enums.RoleEnum;
import com.example.socialnetworkapp.auth.model.AppRole;
import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.model.RefreshToken;
import com.example.socialnetworkapp.auth.model.UserProfileInfo;
import com.example.socialnetworkapp.auth.model.VerificationToken;
import com.example.socialnetworkapp.utils.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthTestUtils {

    public static final String TOKEN = "token";

    public static final String PASSWORD = "P@ssw0rd";

    public static final String ENCRYPTED_PASSWORD = "encryptedP@ssw0rd";

    public static final String EMAIL = "email@email.com";

    public static final String USERNAME = "javaDev111";

    public static final String EXPIRES_AT = "expiresAt";

    public static final Long JWT_EXPIRATION_IN_MILLIS = 90000L;

    public static final String VERIFY_ACCOUNT_SUCCESS_MESSAGE = "Hi %s, your account has been verified.";

    public static final String VERIFY_ACCOUNT_SUCCESS_TITLE = "VERIFICATION SUCCESS";

    public static final String SIGN_UP_SUCCESS_MESSAGE = "Hi %s, we've sent an email to %s. Please click on the link given in email to verify your account.\nThe link in the email will expire in 24 hours.";

    public static final String SIGN_UP_SUCCESS_TITLE = "VERIFICATION LINK SENT";

    public static SignUpRequestDTO buildSignUpRequestDTO() {
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setEmail(EMAIL);
        signUpRequestDTO.setPassword(PASSWORD);
        signUpRequestDTO.setUsername(USERNAME);
        return signUpRequestDTO;
    }

    public static SignInRequestDTO buildSignInRequestDTO() {
        SignInRequestDTO SignInRequestDTO = new SignInRequestDTO();
        SignInRequestDTO.setPassword(PASSWORD);
        SignInRequestDTO.setUsername(USERNAME);
        return SignInRequestDTO;
    }

    public static RefreshTokenRequestDTO buildRefreshTokenRequestDTO() {
        RefreshTokenRequestDTO refreshTokenRequestDTO = new RefreshTokenRequestDTO();
        refreshTokenRequestDTO.setRefreshToken(TOKEN);
        refreshTokenRequestDTO.setUsername(USERNAME);
        return refreshTokenRequestDTO;

    }

    public static AuthenticationResponseDTO buildSignInResponseDTO() {
        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
        authenticationResponseDTO.setAccessToken(TOKEN);
        authenticationResponseDTO.setExpiresAt(EXPIRES_AT);
        authenticationResponseDTO.setRefreshToken(TOKEN);
        return authenticationResponseDTO;
    }

    public static VerificationToken buildVerificationToken() {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setAppUser(buildAppUser(RoleEnum.ROLE_USER));
        verificationToken.setToken(TOKEN);
        return verificationToken;
    }

    public static AppUser buildAppUser(RoleEnum roleEnum) {
        AppUser appUser = new AppUser();
        appUser.setUsername(USERNAME);
        appUser.setEmail(EMAIL);
        appUser.setPassword(ENCRYPTED_PASSWORD);
        appUser.setIsActive(true);
        appUser.setAppRole(buildAppRole(roleEnum));
        return appUser;
    }


    public static AppRole buildAppRole(RoleEnum roleEnum) {
        AppRole appRole = new AppRole();
        appRole.setRoleName(roleEnum);
        return appRole;
    }


    public static RefreshToken buildRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(TOKEN);
        refreshToken.setUsername(USERNAME);
        return refreshToken;
    }

    public static List<GrantedAuthority> buildAuthorityList(RoleEnum... roleEnums) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (RoleEnum roleName : roleEnums) {
            authorityList.add(buildAuthority(roleName));
        }
        return authorityList;
    }

    public static GrantedAuthority buildAuthority(RoleEnum roleEnum) {
        return new SimpleGrantedAuthority(roleEnum.name());
    }


    public static UserRoleUpdateRequestDTO buildUserRoleUpdateRequestDTO(RoleEnum roleEnum) {
        UserRoleUpdateRequestDTO userRoleUpdateRequestDTO = new UserRoleUpdateRequestDTO();
        userRoleUpdateRequestDTO.setUsername(USERNAME);
        userRoleUpdateRequestDTO.setRole(roleEnum);
        return userRoleUpdateRequestDTO;
    }

    public static Map<String, Object> buildClaims(String... roleEnumNames) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.SCOPE, Arrays.asList(roleEnumNames));
        return claims;

    }

    public static UserProfileInfoDTO buildUserProfileInfoRequestDTO() {
        UserProfileInfoDTO userProfileInfoDTO = new UserProfileInfoDTO();
        userProfileInfoDTO.setAddress(RandomStringUtils.randomAlphanumeric(10));
        userProfileInfoDTO.setBirthday(LocalDate.now().minusYears(18L));
        userProfileInfoDTO.setFullName(RandomStringUtils.randomAlphanumeric(10));
        return userProfileInfoDTO;
    }

    public static UserProfileInfo buildUserProfileInfo() {
        UserProfileInfo userProfileInfo = new UserProfileInfo();
        userProfileInfo.setAddress(RandomStringUtils.randomAlphanumeric(10));
        userProfileInfo.setBirthday(LocalDate.now().minusYears(18L));
        userProfileInfo.setFullName(RandomStringUtils.randomAlphanumeric(10));
        return userProfileInfo;
    }
}
