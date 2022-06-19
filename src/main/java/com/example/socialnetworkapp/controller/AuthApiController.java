package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.dto.RegisterRequestDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.service.AuthService;
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
public class AuthApiController implements AuthApi {

    @Autowired
    private AuthService authService;

    @Override
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) throws SocialNetworkAppException {
        authService.signUp(registerRequestDTO);
        return new ResponseEntity<>("User Registration Successful", OK);
    }

}
