package com.example.socialnetworkapp.auth.controller;

import com.example.socialnetworkapp.auth.dto.UserProfileInfoRequestDTO;
import com.example.socialnetworkapp.auth.dto.UserRoleUpdateRequestDTO;
import com.example.socialnetworkapp.auth.service.AccountService;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /*TODO: implement view user profile
     * @GetMapping
     * user with username match user profile can read
     * OR greater than user role
     * */

    @Override
    @PostMapping("/profile/{username}")
    public ResponseEntity<?> createOrUpdateProfile(@PathVariable String username, @RequestBody UserProfileInfoRequestDTO userProfileInfoRequestDTO) throws SocialNetworkAppException {
        SimpleResponseDTO simpleResponseDTO = accountService.createOrUpdateProfile(username, userProfileInfoRequestDTO);
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }
    /*TODO: implement update user profile
     * @PutMapping
     * user with username match user profile can update
     * OR greater than user role
     * */


}
