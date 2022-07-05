package com.example.socialnetworkapp.forum.controller;

import com.example.socialnetworkapp.forum.dto.PostDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

@Api(value = "PostAPI", tags = "Post API")
public interface PostApi {

    @ApiOperation(value = "Create post")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Create post successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 401, message = "[Business Exception] - Unauthorized"),
            @ApiResponse(code = 422, message = "[Business Exception] - Unprocessable Entity"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> create(PostDTO postDTO) throws SocialNetworkAppException;

    @ApiOperation(value = "Find all post")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Find all post successfully"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> findAll(Integer page,
                              Integer size,
                              Sort.Direction direction,
                              String[] properties,
                              String search) throws SocialNetworkAppException;





}
