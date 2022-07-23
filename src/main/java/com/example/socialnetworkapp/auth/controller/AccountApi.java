package com.example.socialnetworkapp.auth.controller;

import com.example.socialnetworkapp.auth.dto.UserProfileInfoDTO;
import com.example.socialnetworkapp.auth.dto.UserRoleUpdateRequestDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(value = "AccountAPI", tags = "Account API")
public interface AccountApi {

    @ApiOperation(value = "Update role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update role successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 422, message = "[Business Exception] - Unprocessable Entity"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> updateRole(@RequestBody @Valid UserRoleUpdateRequestDTO userRoleUpdateRequestDTO) throws SocialNetworkAppException;

    @ApiOperation(value = "Create or update user profile info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Create or update user profile info successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 422, message = "[Business Exception] - Unprocessable Entity"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> createOrUpdateUserProfileInfo(@PathVariable String username, @RequestBody @Valid UserProfileInfoDTO userProfileInfoDTO) throws SocialNetworkAppException;

    @ApiOperation(value = "Get user profile info by username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get user profile info by username successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 422, message = "[Business Exception] - Unprocessable Entity"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> getUserProfileInfoByUsername(@PathVariable String username) throws SocialNetworkAppException;

}
