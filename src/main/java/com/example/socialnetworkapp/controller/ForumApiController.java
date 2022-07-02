package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.dto.ForumDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.service.ForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/forum")
@Slf4j
public class ForumApiController implements ForumApi {

    @Autowired
    private ForumService forumService;

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> createForum(@RequestBody @Valid ForumDTO forumDTO) throws SocialNetworkAppException {
        SimpleResponseDTO forumResponseDTO = forumService.createForum(forumDTO);
        return new ResponseEntity<>(forumResponseDTO, HttpStatus.CREATED);
    }
}
