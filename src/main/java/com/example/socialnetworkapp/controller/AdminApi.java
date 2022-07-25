package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(value = "AdminAPI", tags = "Admin API")
public interface AdminApi {

    @ApiOperation(value = "Get all master message")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Find all master message successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> findAllMasterMessage() throws SocialNetworkAppException;

    @ApiOperation(value = "Get all master error message")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Find all master error message successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> findAllMasterErrorMessage() throws SocialNetworkAppException;


}
