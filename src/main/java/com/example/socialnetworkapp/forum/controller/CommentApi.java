package com.example.socialnetworkapp.forum.controller;

import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.dto.CommentDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

@Api(value = "CommentAPI", tags = "Comment API")
public interface CommentApi {

    @ApiOperation(value = "Create comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Create comment successfully"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 401, message = "[Business Exception] - Unauthorized"),
            @ApiResponse(code = 422, message = "[Business Exception] - Unprocessable Entity"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> create(CommentDTO commentDTO) throws SocialNetworkAppException;

    @ApiOperation(value = "Get all comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get all comment successfully"),
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

    @ApiOperation(value = "Get comment by post id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get comment by post id successfully"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> getByPostId(Long id,
                                  Integer page,
                                  Integer size,
                                  Sort.Direction direction,
                                  String[] properties) throws SocialNetworkAppException;

    @ApiOperation(value = "Get comment created by username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get comment created by username successfully"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "[Business Exception] - Bad request"),
            @ApiResponse(code = 404, message = "[Business Exception] - Not Found"),
            @ApiResponse(code = 500, message = "[System Exception] - Internal server error")
    })
    ResponseEntity<?> getByCreatedBy(String username,
                                     Integer page,
                                     Integer size,
                                     Sort.Direction direction,
                                     String[] properties) throws SocialNetworkAppException;


}
