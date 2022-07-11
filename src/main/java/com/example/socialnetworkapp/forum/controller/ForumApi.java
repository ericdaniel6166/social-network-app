package com.example.socialnetworkapp.forum.controller;

import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.dto.ForumDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

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

    @ApiOperation(value = "Get all forum")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get all forum successfully"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> getAll(Integer page,
                             Integer size,
                             Sort.Direction direction,
                             String[] properties,
                             String search) throws SocialNetworkAppException;

    @ApiOperation(value = "Get forum by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get forum by id successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> getById(Long id) throws SocialNetworkAppException;




}
