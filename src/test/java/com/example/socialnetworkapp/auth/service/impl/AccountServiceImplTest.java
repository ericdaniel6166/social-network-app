package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.dto.UserProfileInfoRequestDTO;
import com.example.socialnetworkapp.auth.dto.UserRoleUpdateRequestDTO;
import com.example.socialnetworkapp.auth.model.UserProfileInfo;
import com.example.socialnetworkapp.auth.service.UserProfileInfoService;
import com.example.socialnetworkapp.enums.ErrorMessageEnum;
import com.example.socialnetworkapp.auth.enums.RoleEnum;
import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.service.RoleService;
import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.enums.MessageEnum;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.service.MasterMessageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

class AccountServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private Authentication authentication;

    @Mock
    private Jwt jwt;

    @Mock
    private RoleService roleService;

    @Mock
    private UserService userService;

    @Mock
    private MasterMessageService masterMessageService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserProfileInfoService userProfileInfoService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenCreateOrUpdateProfile_thenReturnSimpleResponseDTO() throws SocialNetworkAppException {
        AppUser appUser = AuthTestUtils.buildAppUser(RoleEnum.ROLE_USER);
        String username = appUser.getUsername();
        UserProfileInfoRequestDTO userProfileInfoRequestDTO = AuthTestUtils.buildUserProfileInfoRequestDTO();
        UserProfileInfo userProfileInfo = AuthTestUtils.buildUserProfileInfo();
        Mockito.when(userService.findByUsername(username)).thenReturn(appUser);
        Mockito.when(modelMapper.map(userProfileInfoRequestDTO, UserProfileInfo.class)).thenReturn(userProfileInfo);
        Mockito.when(userProfileInfoService.saveAndFlush(userProfileInfo)).thenReturn(userProfileInfo);
        SimpleResponseDTO expected = new SimpleResponseDTO(MessageEnum.MESSAGE_UPDATE_USER_PROFILE_SUCCESS.getTitle(), MessageEnum.MESSAGE_UPDATE_USER_PROFILE_SUCCESS.getMessage());

        SimpleResponseDTO actual = accountService.createOrUpdateProfile(username, userProfileInfoRequestDTO);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenUpdateRole_givenSetRoleToItself_thenThrowAccessDeniedException() throws SocialNetworkAppException {
        AppUser appUserCurrent = AuthTestUtils.buildAppUser(RoleEnum.ROLE_ADMIN);
        String username = appUserCurrent.getUsername();
        Mockito.when(authentication.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(jwt.getSubject()).thenReturn(username);
        UserRoleUpdateRequestDTO userRoleUpdateRequestDTO = AuthTestUtils.buildUserRoleUpdateRequestDTO(RoleEnum.ROLE_ADMIN);
        AccessDeniedException expected = new AccessDeniedException(ErrorMessageEnum.ERROR_MESSAGE_SET_ROLE_YOURSELF.getErrorMessage());

        try {
            accountService.updateRole(userRoleUpdateRequestDTO);
        } catch (AccessDeniedException e){
            Assertions.assertEquals(expected.getMessage(), e.getMessage());
        }
    }

    @Test
    void whenUpdateRole_givenNotRootAdminAccountSetRoleToAdminAccount_thenThrowAccessDeniedException() throws SocialNetworkAppException {
        AppUser appUserCurrent = AuthTestUtils.buildAppUser(RoleEnum.ROLE_ADMIN);
        AppUser appUserUpdate = AuthTestUtils.buildAppUser(RoleEnum.ROLE_ADMIN);
        String usernameUpdate = RandomStringUtils.random(10);
        appUserUpdate.setUsername(usernameUpdate);
        String username = appUserCurrent.getUsername();
        Mockito.when(authentication.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(jwt.getSubject()).thenReturn(username);
        Mockito.when(jwt.getClaims()).thenReturn(AuthTestUtils.buildClaims(RoleEnum.ROLE_ADMIN.name()));
        UserRoleUpdateRequestDTO userRoleUpdateRequestDTO = AuthTestUtils.buildUserRoleUpdateRequestDTO(RoleEnum.ROLE_ADMIN);
        userRoleUpdateRequestDTO.setUsername(usernameUpdate);
        Mockito.when(userService.findByUsername(usernameUpdate)).thenReturn(appUserUpdate);
        AccessDeniedException expected = new AccessDeniedException(ErrorMessageEnum.ERROR_MESSAGE_NOT_HAVE_PERMISSION_SET_ROLE_THIS_USER.getErrorMessage());

        try {
            accountService.updateRole(userRoleUpdateRequestDTO);
        } catch (AccessDeniedException e){
            Assertions.assertEquals(expected.getMessage(), e.getMessage());
        }
    }

    @Test
    void whenUpdateRole_givenNotRootAdminAccountSetRoleAdminToOtherAccount_thenThrowAccessDeniedException() throws SocialNetworkAppException {
        AppUser appUserCurrent = AuthTestUtils.buildAppUser(RoleEnum.ROLE_ADMIN);
        AppUser appUserUpdate = AuthTestUtils.buildAppUser(RoleEnum.ROLE_MODERATOR);
        String usernameUpdate = RandomStringUtils.random(10);
        appUserUpdate.setUsername(usernameUpdate);
        String username = appUserCurrent.getUsername();
        Mockito.when(authentication.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(jwt.getSubject()).thenReturn(username);
        Mockito.when(jwt.getClaims()).thenReturn(AuthTestUtils.buildClaims(RoleEnum.ROLE_ADMIN.name()));
        UserRoleUpdateRequestDTO userRoleUpdateRequestDTO = AuthTestUtils.buildUserRoleUpdateRequestDTO(RoleEnum.ROLE_ADMIN);
        userRoleUpdateRequestDTO.setUsername(usernameUpdate);
        Mockito.when(userService.findByUsername(usernameUpdate)).thenReturn(appUserUpdate);
        AccessDeniedException expected = new AccessDeniedException(ErrorMessageEnum.ERROR_MESSAGE_NOT_HAVE_PERMISSION_SET_THIS_ROLE.getErrorMessage());

        try {
            accountService.updateRole(userRoleUpdateRequestDTO);
        } catch (AccessDeniedException e){
            Assertions.assertEquals(expected.getMessage(), e.getMessage());
        }
    }

    @Test
    void whenUpdateRole_givenNotRootAdminAccountSetRoleModeratorToOtherAccount_thenThrowAccessDeniedException() throws SocialNetworkAppException {
        AppUser appUserCurrent = AuthTestUtils.buildAppUser(RoleEnum.ROLE_ADMIN);
        AppUser appUserUpdate = AuthTestUtils.buildAppUser(RoleEnum.ROLE_MODERATOR);
        String usernameUpdate = RandomStringUtils.random(10);
        appUserUpdate.setUsername(usernameUpdate);
        String username = appUserCurrent.getUsername();
        Mockito.when(authentication.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(jwt.getSubject()).thenReturn(username);
        Mockito.when(jwt.getClaims()).thenReturn(AuthTestUtils.buildClaims(RoleEnum.ROLE_ADMIN.name()));
        UserRoleUpdateRequestDTO userRoleUpdateRequestDTO = AuthTestUtils.buildUserRoleUpdateRequestDTO(RoleEnum.ROLE_MODERATOR);
        userRoleUpdateRequestDTO.setUsername(usernameUpdate);
        Mockito.when(userService.findByUsername(usernameUpdate)).thenReturn(appUserUpdate);
        AccessDeniedException expected = new AccessDeniedException(ErrorMessageEnum.ERROR_MESSAGE_USER_ALREADY_HAD_THIS_ROLE.getErrorMessage());

        try {
            accountService.updateRole(userRoleUpdateRequestDTO);
        } catch (SocialNetworkAppException e){
            Assertions.assertEquals(expected.getMessage(), e.getMessage());
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.name(), e.getError());
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
        }
    }

    @Test
    void whenUpdateRole_givenNotRootAdminAccountSetRoleUserToOtherAccount_thenReturnSimpleResponseDTO() throws SocialNetworkAppException {
        AppUser appUserCurrent = AuthTestUtils.buildAppUser(RoleEnum.ROLE_ADMIN);
        AppUser appUserUpdate = AuthTestUtils.buildAppUser(RoleEnum.ROLE_MODERATOR);
        String usernameUpdate = RandomStringUtils.random(10);
        appUserUpdate.setUsername(usernameUpdate);
        String username = appUserCurrent.getUsername();
        Mockito.when(authentication.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(jwt.getSubject()).thenReturn(username);
        Mockito.when(jwt.getClaims()).thenReturn(AuthTestUtils.buildClaims(RoleEnum.ROLE_ADMIN.name()));
        RoleEnum roleEnumNew = RoleEnum.ROLE_USER;
        UserRoleUpdateRequestDTO userRoleUpdateRequestDTO = AuthTestUtils.buildUserRoleUpdateRequestDTO(roleEnumNew);
        userRoleUpdateRequestDTO.setUsername(usernameUpdate);
        Mockito.when(userService.findByUsername(usernameUpdate)).thenReturn(appUserUpdate);
        Mockito.when(roleService.findByRoleName(userRoleUpdateRequestDTO.getRole())).thenReturn(AuthTestUtils.buildAppRole(roleEnumNew));
        SimpleResponseDTO expected = CommonTestUtils.buildSimpleResponseDTO();
        MasterMessage masterMessage = CommonTestUtils.buildMasterMessage(MasterMessageCode.UPDATE_ROLE_SUCCESS);
        Mockito.when(masterMessageService.findByMessageCode(MasterMessageCode.UPDATE_ROLE_SUCCESS)).thenReturn(masterMessage);

        SimpleResponseDTO actual = accountService.updateRole(userRoleUpdateRequestDTO);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenUpdateRole_givenRootAdminAccountSetRoleModeratorToOtherAccount_thenThrowAccessDeniedException() throws SocialNetworkAppException {
        AppUser appUserCurrent = AuthTestUtils.buildAppUser(RoleEnum.ROLE_ROOT_ADMIN);
        AppUser appUserUpdate = AuthTestUtils.buildAppUser(RoleEnum.ROLE_MODERATOR);
        String usernameUpdate = RandomStringUtils.random(10);
        appUserUpdate.setUsername(usernameUpdate);
        String username = appUserCurrent.getUsername();
        Mockito.when(authentication.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(jwt.getSubject()).thenReturn(username);
        Mockito.when(jwt.getClaims()).thenReturn(AuthTestUtils.buildClaims(RoleEnum.ROLE_ROOT_ADMIN.name()));
        UserRoleUpdateRequestDTO userRoleUpdateRequestDTO = AuthTestUtils.buildUserRoleUpdateRequestDTO(RoleEnum.ROLE_MODERATOR);
        userRoleUpdateRequestDTO.setUsername(usernameUpdate);
        Mockito.when(userService.findByUsername(usernameUpdate)).thenReturn(appUserUpdate);
        AccessDeniedException expected = new AccessDeniedException(ErrorMessageEnum.ERROR_MESSAGE_USER_ALREADY_HAD_THIS_ROLE.getErrorMessage());

        try {
            accountService.updateRole(userRoleUpdateRequestDTO);
        } catch (SocialNetworkAppException e){
            Assertions.assertEquals(expected.getMessage(), e.getMessage());
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.name(), e.getError());
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
        }
    }

}