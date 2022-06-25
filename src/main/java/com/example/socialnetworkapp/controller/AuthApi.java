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
            @ApiResponse(code = 201, message = "Sign up successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 422, message = "[Business Exception] - Request is not valid"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> signUp(RegisterRequestDTO registerRequestDTO) throws SocialNetworkAppException;
}
