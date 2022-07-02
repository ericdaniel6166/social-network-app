package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.dto.ForumDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(value = "ForumAPI", tags = "Forum API")
public interface ForumApi {

    @ApiOperation(value = "Create forum")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Create forum successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 401, message = "[Business Exception] - Unauthorized"),
            @ApiResponse(code = 422, message = "[Business Exception] - Unprocessable Entity"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> createForum(ForumDTO forumDTO) throws SocialNetworkAppException;

//    @ApiOperation(value = "Sign up")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Verify account successfully"),
//            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
//            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
//            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
//    })
//    ResponseEntity<?> verifyAccount(String token) throws SocialNetworkAppException;
//
//    @ApiOperation(value = "Sign in")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Sign in successfully"),
//            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
//            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
//            @ApiResponse(code = 422, message = "[Business Exception] - Unprocessable Entity"),
//            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
//    })
//    ResponseEntity<?> signIn(SignInRequestDTO signInRequestDTO) throws SocialNetworkAppException;


}
