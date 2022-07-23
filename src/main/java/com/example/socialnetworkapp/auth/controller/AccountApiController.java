package com.example.socialnetworkapp.auth.controller;

import com.example.socialnetworkapp.auth.dto.UserProfileInfoDTO;
import com.example.socialnetworkapp.auth.dto.UserRoleUpdateRequestDTO;
import com.example.socialnetworkapp.auth.service.AccountService;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
public class AccountApiController implements AccountApi {

    private final AccountService accountService;

    @Override
    @PutMapping("/updateRole")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> updateRole(@RequestBody @Valid UserRoleUpdateRequestDTO userRoleUpdateRequestDTO) throws SocialNetworkAppException {
        SimpleResponseDTO simpleResponseDTO = accountService.updateRole(userRoleUpdateRequestDTO);
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }

    /*TODO: implement block account
     * @PutMapping
     * @PreAuthorize(hasAuthority('SCOPE_ROLE_MODERATOR')")
     * user can't block himself
     * ROLE_MODERATOR can't block greater and equals ROLE_MODERATOR
     * ROLE_ADMIN can't block greater and equals ROLE_ADMIN
     * ROLE_ROOT_ADMIN can block any account
     *
     * */

    @Override
    @PostMapping("/profile/{username}")
    @PreAuthorize("#username.equals(authentication.principal.subject)")
    public ResponseEntity<?> createOrUpdateUserProfileInfo(@PathVariable String username, @RequestBody @Valid UserProfileInfoDTO userProfileInfoDTO) throws SocialNetworkAppException {
        SimpleResponseDTO simpleResponseDTO = accountService.createOrUpdateUserProfileInfo(username, userProfileInfoDTO);
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }

    @Override
    @GetMapping("/profile/{username}")
    @PreAuthorize("#username.equals(authentication.principal.subject) or hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> getUserProfileInfoByUsername(@PathVariable String username) throws SocialNetworkAppException {
        UserProfileInfoDTO userProfileInfoDTO = accountService.getUserProfileInfoByUsername(username);
        return new ResponseEntity<>(userProfileInfoDTO, HttpStatus.OK);
    }

}
