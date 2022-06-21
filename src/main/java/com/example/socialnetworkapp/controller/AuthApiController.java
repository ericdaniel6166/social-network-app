package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.dto.RegisterRequestDTO;
import com.example.socialnetworkapp.dto.RegisterResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthApiController implements AuthApi {

    @Autowired
    private AuthService authService;

    @Override
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) throws SocialNetworkAppException {
        log.info("Start sign up");
        RegisterResponseDTO registerResponseDTO = authService.signUp(registerRequestDTO);
        log.info("End sign up");
        return new ResponseEntity<>(registerResponseDTO, OK);
    }

}
