package com.example.socialnetworkapp.forum.controller;

import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.dto.PostDTO;
import com.example.socialnetworkapp.forum.service.PostService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Override
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "page", required = false, defaultValue = Constants.PAGE_REQUEST_PAGE_NUMBER_DEFAULT) Integer page,
                                    @RequestParam(name = "size", required = false, defaultValue = Constants.PAGE_REQUEST_SIZE_DEFAULT) Integer size,
                                    @RequestParam(name = "direction", required = false, defaultValue = Constants.SORT_DIRECTION_DESC) @Valid Sort.Direction direction,
                                    @RequestParam(name = "properties", required = false, defaultValue = Constants.PAGE_REQUEST_PROPERTIES_LAST_MODIFIED_DATE) String[] properties,
                                    @RequestParam(name = "search", required = false) String search) throws SocialNetworkAppException {
        Pageable pageable = CommonUtils.buildPageable(page, size, direction, properties);
        Page<PostDTO> postDTOPage = postService.getAll(pageable, search);
        return CommonUtils.buildPageResponseEntity(postDTOPage);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws SocialNetworkAppException {
        PostDTO postDTO = postService.getById(id);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @Override
    @GetMapping("/forum/{id}")
    public ResponseEntity<?> getByForumId(@PathVariable Long id,
                                          @RequestParam(name = "page", required = false, defaultValue = Constants.PAGE_REQUEST_PAGE_NUMBER_DEFAULT) Integer page,
                                          @RequestParam(name = "size", required = false, defaultValue = Constants.PAGE_REQUEST_SIZE_DEFAULT) Integer size,
                                          @RequestParam(name = "direction", required = false, defaultValue = Constants.SORT_DIRECTION_DESC) @Valid Sort.Direction direction,
                                          @RequestParam(name = "properties", required = false, defaultValue = Constants.PAGE_REQUEST_PROPERTIES_LAST_MODIFIED_DATE) String[] properties) throws SocialNetworkAppException {
        Pageable pageable = CommonUtils.buildPageable(page, size, direction, properties);
        Page<PostDTO> postDTOPage = postService.getByForumId(id, pageable);
        return CommonUtils.buildPageResponseEntity(postDTOPage);
    }

    @Override
    @GetMapping("/createdBy/{username}")
    public ResponseEntity<?> getByCreatedBy(@PathVariable String username,
                                            @RequestParam(name = "page", required = false, defaultValue = Constants.PAGE_REQUEST_PAGE_NUMBER_DEFAULT) Integer page,
                                            @RequestParam(name = "size", required = false, defaultValue = Constants.PAGE_REQUEST_SIZE_DEFAULT) Integer size,
                                            @RequestParam(name = "direction", required = false, defaultValue = Constants.SORT_DIRECTION_DESC) @Valid Sort.Direction direction,
                                            @RequestParam(name = "properties", required = false, defaultValue = Constants.PAGE_REQUEST_PROPERTIES_LAST_MODIFIED_DATE) String[] properties) throws SocialNetworkAppException {
        Pageable pageable = CommonUtils.buildPageable(page, size, direction, properties);
        Page<PostDTO> postDTOPage = postService.getByCreatedBy(username, pageable);
        return CommonUtils.buildPageResponseEntity(postDTOPage);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("@customPermissionEvaluator.isMatchedPostCreatedBy(#id) or hasAuthority('SCOPE_ROLE_MODERATOR')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws SocialNetworkAppException {
        SimpleResponseDTO simpleResponseDTO = postService.deleteById(id);
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }
}
