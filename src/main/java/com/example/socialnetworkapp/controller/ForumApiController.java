package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.dto.ForumDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.service.ForumService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/forum")
@Slf4j
public class ForumApiController implements ForumApi {

    @Autowired
    private ForumService forumService;

    @Override
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "page", required = false, defaultValue = Constants.PAGE_REQUEST_PAGE_NUMBER_DEFAULT) Integer page,
                                     @RequestParam(name = "size", required = false, defaultValue = Constants.PAGE_REQUEST_SIZE_DEFAULT) Integer size,
                                     @RequestParam(name = "direction", required = false, defaultValue = Constants.PAGE_REQUEST_DIRECTION_DESC) String direction,
                                     @RequestParam(name = "properties", required = false, defaultValue = Constants.PAGE_REQUEST_PROPERTIES_LAST_MODIFIED_DATE) String[] properties) throws SocialNetworkAppException {

        Pageable pageable = CommonUtils.buildPageable(page, size, direction, properties);
        Page<ForumDTO> forumDTOPage = forumService.findAll(pageable);
        return new ResponseEntity<>(forumDTOPage, HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ForumDTO forumDTO) throws SocialNetworkAppException {
        SimpleResponseDTO forumResponseDTO = forumService.create(forumDTO);
        return new ResponseEntity<>(forumResponseDTO, HttpStatus.CREATED);
    }

}
