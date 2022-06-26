package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.dto.SignUpRequestDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthApiController implements AuthApi {

    @Autowired
    private AuthService authService;

    @Override
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) throws SocialNetworkAppException {
        log.info("Start sign up");
        SimpleResponseDTO simpleResponseDTO = authService.signUp(signUpRequestDTO);
        log.info("End sign up");
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/verifyAccount/{token}")
    public ResponseEntity<?> verifyAccount(@PathVariable @NotBlank String token) throws SocialNetworkAppException {
        log.info("Start verify account");
        SimpleResponseDTO simpleResponseDTO = authService.verifyAccount(token);
        log.info("End verify account");
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }

}
