package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.dto.UserDTO;
import com.example.socialnetworkapp.auth.enums.RoleEnum;
import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.service.RoleService;
import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.service.MasterMessageService;
import com.example.socialnetworkapp.utils.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenUpdateRole_givenSetRoleToItself_thenThrowAccessDeniedException() throws SocialNetworkAppException {
        AppUser appUserCurrent = AuthTestUtils.buildAppUser(RoleEnum.ROLE_ADMIN);
        String username = appUserCurrent.getUsername();
        Mockito.when(authentication.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(jwt.getSubject()).thenReturn(username);
        UserDTO userDTO = AuthTestUtils.buildUserDTO(RoleEnum.ROLE_ADMIN);
        AccessDeniedException expected = new AccessDeniedException(Constants.ERROR_MESSAGE_SET_ROLE_YOURSELF);

        try {
            accountService.updateRole(userDTO);
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
        UserDTO userDTO = AuthTestUtils.buildUserDTO(RoleEnum.ROLE_ADMIN);
        userDTO.setUsername(usernameUpdate);
        Mockito.when(userService.findByUsername(usernameUpdate)).thenReturn(appUserUpdate);
        AccessDeniedException expected = new AccessDeniedException(Constants.ERROR_MESSAGE_NOT_HAVE_PERMISSION_SET_ROLE_THIS_USER);

        try {
            accountService.updateRole(userDTO);
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
        UserDTO userDTO = AuthTestUtils.buildUserDTO(RoleEnum.ROLE_ADMIN);
        userDTO.setUsername(usernameUpdate);
        Mockito.when(userService.findByUsername(usernameUpdate)).thenReturn(appUserUpdate);
        AccessDeniedException expected = new AccessDeniedException(Constants.ERROR_MESSAGE_NOT_HAVE_PERMISSION_SET_THIS_ROLE);

        try {
            accountService.updateRole(userDTO);
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
        UserDTO userDTO = AuthTestUtils.buildUserDTO(RoleEnum.ROLE_MODERATOR);
        userDTO.setUsername(usernameUpdate);
        Mockito.when(userService.findByUsername(usernameUpdate)).thenReturn(appUserUpdate);
        AccessDeniedException expected = new AccessDeniedException(Constants.ERROR_MESSAGE_USER_ALREADY_HAD_THIS_ROLE);

        try {
            accountService.updateRole(userDTO);
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
        UserDTO userDTO = AuthTestUtils.buildUserDTO(roleEnumNew);
        userDTO.setUsername(usernameUpdate);
        Mockito.when(userService.findByUsername(usernameUpdate)).thenReturn(appUserUpdate);
        Mockito.when(roleService.findByRoleName(userDTO.getRole())).thenReturn(AuthTestUtils.buildAppRole(roleEnumNew));
        SimpleResponseDTO expected = CommonTestUtils.buildSimpleResponseDTO();
        MasterMessage masterMessage = CommonTestUtils.buildMasterMessage(MasterMessageCode.UPDATE_ROLE_SUCCESS);
        Mockito.when(masterMessageService.findByMessageCode(MasterMessageCode.UPDATE_ROLE_SUCCESS)).thenReturn(masterMessage);

        SimpleResponseDTO actual = accountService.updateRole(userDTO);

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
        UserDTO userDTO = AuthTestUtils.buildUserDTO(RoleEnum.ROLE_MODERATOR);
        userDTO.setUsername(usernameUpdate);
        Mockito.when(userService.findByUsername(usernameUpdate)).thenReturn(appUserUpdate);
        AccessDeniedException expected = new AccessDeniedException(Constants.ERROR_MESSAGE_USER_ALREADY_HAD_THIS_ROLE);

        try {
            accountService.updateRole(userDTO);
        } catch (SocialNetworkAppException e){
            Assertions.assertEquals(expected.getMessage(), e.getMessage());
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.name(), e.getError());
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
        }
    }








}