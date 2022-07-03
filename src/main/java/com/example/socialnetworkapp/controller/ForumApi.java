package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.dto.ForumDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value = "ForumAPI", tags = "Forum API")
public interface ForumApi {

    @ApiOperation(value = "Create forum")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Create forum successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 401, message = "[Business Exception] - Unauthorized"),
            @ApiResponse(code = 422, message = "[Business Exception] - Unprocessable Entity"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> create(ForumDTO forumDTO) throws SocialNetworkAppException;

    @ApiOperation(value = "Find all forum")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Find all forum successfully"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> findAll(Integer page,
                              Integer size,
                              Sort.Direction direction,
                              String[] properties) throws SocialNetworkAppException;


}
