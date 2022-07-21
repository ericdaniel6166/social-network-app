package com.example.socialnetworkapp.auth;

import com.example.socialnetworkapp.auth.dto.SignInRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignInResponseDTO;
import com.example.socialnetworkapp.auth.dto.SignUpRequestDTO;
import com.example.socialnetworkapp.auth.dto.UserRoleUpdateDTO;
import com.example.socialnetworkapp.auth.enums.RoleEnum;
import com.example.socialnetworkapp.auth.model.AppRole;
import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.model.RefreshToken;
import com.example.socialnetworkapp.auth.model.VerificationToken;
import com.example.socialnetworkapp.utils.Constants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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

    public static SignInResponseDTO buildSignInResponseDTO() {
        SignInResponseDTO signInResponseDTO = new SignInResponseDTO();
        signInResponseDTO.setAccessToken(TOKEN);
        signInResponseDTO.setExpiresAt(EXPIRES_AT);
        signInResponseDTO.setRefreshToken(TOKEN);
        return signInResponseDTO;
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
        return refreshToken;
    }

    public static List<GrantedAuthority> buildAuthorityList(RoleEnum... roleEnums) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (RoleEnum roleName: roleEnums){
            authorityList.add(buildAuthority(roleName));
        }
        return authorityList;
    }

    public static GrantedAuthority buildAuthority(RoleEnum roleEnum) {
        return new SimpleGrantedAuthority(roleEnum.name());
    }


    public static UserRoleUpdateDTO buildUserDTO(RoleEnum roleEnum) {
        UserRoleUpdateDTO userRoleUpdateDTO = new UserRoleUpdateDTO();
        userRoleUpdateDTO.setUsername(USERNAME);
        userRoleUpdateDTO.setRole(roleEnum);
        return userRoleUpdateDTO;
    }

    public static Map<String, Object> buildClaims(String... roleEnumNames) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.SCOPE, Arrays.asList(roleEnumNames));
        return claims;

    }
}
