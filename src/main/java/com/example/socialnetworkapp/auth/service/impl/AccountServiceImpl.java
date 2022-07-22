package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.auth.dto.UserProfileInfoRequestDTO;
import com.example.socialnetworkapp.auth.dto.UserRoleUpdateRequestDTO;
import com.example.socialnetworkapp.auth.model.UserProfileInfo;
import com.example.socialnetworkapp.auth.service.UserProfileInfoService;
import com.example.socialnetworkapp.enums.ErrorMessageEnum;
import com.example.socialnetworkapp.auth.enums.RoleEnum;
import com.example.socialnetworkapp.auth.model.AppRole;
import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.service.AccountService;
import com.example.socialnetworkapp.auth.service.RoleService;
import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.enums.MessageEnum;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.service.MasterMessageService;
import com.example.socialnetworkapp.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserService userService;

    private final RoleService roleService;

    private final MasterMessageService masterMessageService;

    private final ModelMapper modelMapper;

    private final UserProfileInfoService userProfileInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleResponseDTO createOrUpdateProfile(String username, UserProfileInfoRequestDTO userProfileInfoRequestDTO) throws ResourceNotFoundException {
        log.info("Create or update profile for username: {}", username);
        AppUser appUser = userService.findByUsername(username);
        UserProfileInfo userProfileInfo = modelMapper.map(userProfileInfoRequestDTO, UserProfileInfo.class);
        userProfileInfo.setAppUser(appUser);
        userProfileInfoService.saveAndFlush(userProfileInfo);
        return new SimpleResponseDTO(MessageEnum.MESSAGE_UPDATE_USER_PROFILE_SUCCESS.getTitle(), MessageEnum.MESSAGE_UPDATE_USER_PROFILE_SUCCESS.getMessage());
    }

    /**
     * user can't set role to himself
     * user can't set role to account which has already had that role (ex: error set ROLE_MODERATOR to account has ROLE_MODERATOR)
     * ROLE_ROOT_ADMIN can:
     * . set any roles to any accounts
     * ROLE_ADMIN can:
     * . set role less or equal ROLE_MODERATOR to less or equal ROLE_MODERATOR account
     * less or equal ROLE_MODERATOR:
     * . Forbidden
     *
     * @param userRoleUpdateRequestDTO
     * @throws ResourceNotFoundException
     */
    @Override
    public SimpleResponseDTO updateRole(UserRoleUpdateRequestDTO userRoleUpdateRequestDTO) throws SocialNetworkAppException {
        RoleEnum roleEnumNew = userRoleUpdateRequestDTO.getRole();
        String usernameUpdate = userRoleUpdateRequestDTO.getUsername();
        String currentUsername = CommonUtils.getCurrentUsername();
        if (StringUtils.equals(currentUsername, usernameUpdate)) {
            log.error(ErrorMessageEnum.ERROR_MESSAGE_SET_ROLE_YOURSELF.getErrorMessage());
            throw new AccessDeniedException(ErrorMessageEnum.ERROR_MESSAGE_SET_ROLE_YOURSELF.getErrorMessage());
        }
        AppUser appUserUpdate = userService.findByUsername(usernameUpdate);
        AppRole appRoleOld = appUserUpdate.getAppRole();
        if (!CommonUtils.hasAuthority(RoleEnum.ROLE_ROOT_ADMIN)) {
            if (appRoleOld.getRoleName().compareTo(RoleEnum.ROLE_ADMIN) >= 0) {
                log.error(ErrorMessageEnum.ERROR_MESSAGE_NOT_HAVE_PERMISSION_SET_ROLE_THIS_USER.getErrorMessage());
                throw new AccessDeniedException(ErrorMessageEnum.ERROR_MESSAGE_NOT_HAVE_PERMISSION_SET_ROLE_THIS_USER.getErrorMessage());
            }
            if (roleEnumNew.compareTo(RoleEnum.ROLE_ADMIN) >= 0) {
                log.error(ErrorMessageEnum.ERROR_MESSAGE_NOT_HAVE_PERMISSION_SET_THIS_ROLE.getErrorMessage());
                throw new AccessDeniedException(ErrorMessageEnum.ERROR_MESSAGE_NOT_HAVE_PERMISSION_SET_THIS_ROLE.getErrorMessage());
            }
        }
        if (roleEnumNew.compareTo(appRoleOld.getRoleName()) == 0) {
            log.error(ErrorMessageEnum.ERROR_MESSAGE_USER_ALREADY_HAD_THIS_ROLE.getErrorMessage());
            throw new SocialNetworkAppException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(),
                    ErrorMessageEnum.ERROR_MESSAGE_USER_ALREADY_HAD_THIS_ROLE.getErrorMessage(), null);
        }
        AppRole appRoleNew = roleService.findByRoleName(roleEnumNew);
        appUserUpdate.setAppRole(appRoleNew);
        userService.saveAndFlush(appUserUpdate);
        MasterMessage masterMessage = masterMessageService.findByMessageCode(MasterMessageCode.UPDATE_ROLE_SUCCESS);
        SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
        simpleResponseDTO.setTitle(StringEscapeUtils.unescapeJava(masterMessage.getTitle()));
        simpleResponseDTO.setMessage(StringEscapeUtils.unescapeJava(CommonUtils.formatString(masterMessage.getMessage(), roleEnumNew.name(), usernameUpdate)));
        return simpleResponseDTO;


    }

}
