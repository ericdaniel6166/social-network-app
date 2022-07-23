package com.example.socialnetworkapp.auth.controller;

import com.example.socialnetworkapp.auth.dto.SignInRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignInResponseDTO;
import com.example.socialnetworkapp.auth.dto.SignUpRequestDTO;
import com.example.socialnetworkapp.auth.service.AuthService;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthApiController implements AuthApi {

    private final AuthService authService;

    @Override
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) throws SocialNetworkAppException {
        SimpleResponseDTO simpleResponseDTO = authService.signUp(signUpRequestDTO);
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }

    @Override
    @GetMapping("/verifyAccount/{token}")
    public ResponseEntity<?> verifyAccount(@PathVariable String token) throws SocialNetworkAppException {
        SimpleResponseDTO simpleResponseDTO = authService.verifyAccount(token);
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }

    @Override
    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequestDTO signInRequestDTO) throws SocialNetworkAppException {
        SignInResponseDTO signInResponseDTO = authService.signIn(signInRequestDTO);
        return new ResponseEntity<>(signInResponseDTO, HttpStatus.OK);
    }

}
