package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.dto.RegisterRequestDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(value = "AuthAPI", tags = "Auth API")
public interface AuthApi {

    @ApiOperation(value = "Sign up")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "[Business Exception] - BAD_REQUEST"),
            @ApiResponse(code = 422, message = "[Business Exception] - UNPROCESSABLE_ENTITY"),
            @ApiResponse(code = 500, message = "[System Exception] - INTERNAL_SERVER_ERROR")
    })
    ResponseEntity<?> signUp(RegisterRequestDTO registerRequestDTO) throws SocialNetworkAppException;
}
