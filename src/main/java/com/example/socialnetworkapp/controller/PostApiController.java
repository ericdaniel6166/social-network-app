package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.dto.PostDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/post")
@Slf4j
@RequiredArgsConstructor
public class PostApiController implements PostApi {

    private final PostService postService;

    @Override
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid PostDTO postDTO) throws SocialNetworkAppException {
        SimpleResponseDTO simpleResponseDTO = postService.create(postDTO);
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }
}
