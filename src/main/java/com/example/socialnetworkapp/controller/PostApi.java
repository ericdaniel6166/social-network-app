package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.dto.PostDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(value = "PostAPI", tags = "Post API")
public interface PostApi {

    @ApiOperation(value = "Create forum")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Create forum successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 401, message = "[Business Exception] - Unauthorized"),
            @ApiResponse(code = 422, message = "[Business Exception] - Unprocessable Entity"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> create(PostDTO postDTO) throws SocialNetworkAppException;


}
