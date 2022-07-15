package com.example.socialnetworkapp.auth.controller;

import com.example.socialnetworkapp.auth.dto.UserDTO;
import com.example.socialnetworkapp.auth.service.AccountService;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<?> updateRole(@RequestBody @Valid UserDTO userDTO) throws SocialNetworkAppException {
        SimpleResponseDTO simpleResponseDTO = accountService.updateRole(userDTO);
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }


}
