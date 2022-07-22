package com.example.socialnetworkapp.forum.controller;

import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.dto.PostDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

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
    ResponseEntity<?> create(@RequestBody @Valid PostDTO postDTO) throws SocialNetworkAppException;

    @ApiOperation(value = "Get all post")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get all post successfully"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> getAll(@RequestParam Integer page,
                             @RequestParam Integer size,
                             @RequestParam Sort.Direction direction,
                             @RequestParam String[] properties,
                             @RequestParam String search) throws SocialNetworkAppException;

    @ApiOperation(value = "Get post by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get post by id successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> getById(@PathVariable Long id) throws SocialNetworkAppException;

    @ApiOperation(value = "Get post by forum id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get post by forum id successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> getByForumId(@PathVariable Long id,
                                   @RequestParam Integer page,
                                   @RequestParam Integer size,
                                   @RequestParam Sort.Direction direction,
                                   @RequestParam String[] properties) throws SocialNetworkAppException;

    @ApiOperation(value = "Get post created by username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get post created by username successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> getByCreatedBy(@PathVariable String username,
                                     @RequestParam Integer page,
                                     @RequestParam Integer size,
                                     @RequestParam Sort.Direction direction,
                                     @RequestParam String[] properties) throws SocialNetworkAppException;

    @ApiOperation(value = "Delete post by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete post by id successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 401, message = "[Business Exception] - Unauthorized"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> deleteById(@PathVariable Long id) throws SocialNetworkAppException;


}
