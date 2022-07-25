package com.example.socialnetworkapp.auth.controller;

import com.example.socialnetworkapp.auth.dto.RefreshTokenRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignInRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignUpRequestDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(value = "AuthAPI", tags = "Auth API")
public interface AuthApi {

    @ApiOperation(value = "Sign up")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sign up successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 409, message = "[Business Exception] - Conflict"),
            @ApiResponse(code = 422, message = "[Business Exception] - Unprocessable Entity"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) throws SocialNetworkAppException;

    @ApiOperation(value = "Verify account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Verify account successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> verifyAccount(@PathVariable String token) throws SocialNetworkAppException;

    @ApiOperation(value = "Sign in")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sign in successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 422, message = "[Business Exception] - Unprocessable Entity"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> signIn(@RequestBody @Valid SignInRequestDTO signInRequestDTO) throws SocialNetworkAppException;

    @ApiOperation(value = "Refresh token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Refresh token successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 422, message = "[Business Exception] - Unprocessable Entity"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequestDTO refreshTokenRequestDTO) throws SocialNetworkAppException;

    @ApiOperation(value = "Sign out")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sign out successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 422, message = "[Business Exception] - Unprocessable Entity"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> signOut(@RequestBody @Valid RefreshTokenRequestDTO refreshTokenRequestDTO) throws SocialNetworkAppException;


}
