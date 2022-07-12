package com.example.socialnetworkapp.forum.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.enums.AppRoleName;
import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.ForumTestUtils;
import com.example.socialnetworkapp.forum.dto.CommentDTO;
import com.example.socialnetworkapp.forum.model.AppComment;
import com.example.socialnetworkapp.forum.model.Post;
import com.example.socialnetworkapp.forum.repository.CommentRepository;
import com.example.socialnetworkapp.forum.service.PostService;
import com.example.socialnetworkapp.utils.Constants;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

class CommentServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private PostService postService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenSaveAndFlush_givenAppComment_thenReturnAppComment() {
        AppComment expected = ForumTestUtils.buildAppComment();
        Mockito.when(commentRepository.saveAndFlush(expected)).thenReturn(expected);

        AppComment actual = commentService.saveAndFlush(expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenFindAll_givenNotBlankSearch_thenReturnCommentDTOPage() throws SocialNetworkAppException {
        String search = CommonTestUtils.SEARCH;
        CommentDTO commentDTO = ForumTestUtils.buildCommentDTO();
        AppComment appComment = ForumTestUtils.buildAppComment();
        Pageable pageable = CommonTestUtils.buildPageable();
        Page<AppComment> commentPage = (Page<AppComment>) CommonTestUtils.buildPage(appComment, appComment);
        Page<CommentDTO> expected = (Page<CommentDTO>) CommonTestUtils.buildPage(commentDTO, commentDTO);
        Mockito.when(commentRepository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable))).thenReturn(commentPage);
        Mockito.when(modelMapper.map(appComment, CommentDTO.class)).thenReturn(commentDTO);

        Page<CommentDTO> actual = commentService.getAll(pageable, search);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenFindAll_givenBlankSearch_thenReturnCommentDTOPage() throws SocialNetworkAppException {
        String search = StringUtils.SPACE;
        CommentDTO commentDTO = ForumTestUtils.buildCommentDTO();
        AppComment appComment = ForumTestUtils.buildAppComment();
        Pageable pageable = CommonTestUtils.buildPageable();
        Page<AppComment> commentPage = (Page<AppComment>) CommonTestUtils.buildPage(appComment, appComment);
        Page<CommentDTO> expected = (Page<CommentDTO>) CommonTestUtils.buildPage(commentDTO, commentDTO);
        Mockito.when(commentRepository.findAllByIsActiveTrue(pageable)).thenReturn(commentPage);
        Mockito.when(modelMapper.map(appComment, CommentDTO.class)).thenReturn(commentDTO);

        Page<CommentDTO> actual = commentService.getAll(pageable, search);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenGetByPostId_thenReturnCommentDTOPage() throws SocialNetworkAppException {
        CommentDTO commentDTO = ForumTestUtils.buildCommentDTO();
        AppComment appComment = ForumTestUtils.buildAppComment();
        Long id = commentDTO.getPostId();
        Pageable pageable = CommonTestUtils.buildPageable();
        Page<AppComment> commentPage = (Page<AppComment>) CommonTestUtils.buildPage(appComment, appComment);
        Page<CommentDTO> expected = (Page<CommentDTO>) CommonTestUtils.buildPage(commentDTO, commentDTO);
        Mockito.when(commentRepository.findAllByIsActiveTrueAndPost_Id(id, pageable)).thenReturn(commentPage);
        Mockito.when(modelMapper.map(appComment, CommentDTO.class)).thenReturn(commentDTO);

        Page<CommentDTO> actual = commentService.getByPostId(id, pageable);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenGetByCreatedBy_thenReturnCommentDTOPage() throws SocialNetworkAppException {
        CommentDTO commentDTO = ForumTestUtils.buildCommentDTO();
        AppComment appComment = ForumTestUtils.buildAppComment();
        Pageable pageable = CommonTestUtils.buildPageable();
        String username = AuthTestUtils.USERNAME;
        Page<AppComment> commentPage = (Page<AppComment>) CommonTestUtils.buildPage(appComment, appComment);
        Page<CommentDTO> expected = (Page<CommentDTO>) CommonTestUtils.buildPage(commentDTO, commentDTO);
        Mockito.when(commentRepository.findAllByIsActiveTrueAndCreatedBy(username, pageable)).thenReturn(commentPage);
        Mockito.when(modelMapper.map(appComment, CommentDTO.class)).thenReturn(commentDTO);

        Page<CommentDTO> actual = commentService.getByCreatedBy(username, pageable);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenFindById_givenNotEmptyAppComment_thenReturnAppComment() throws SocialNetworkAppException {
        AppComment expected = ForumTestUtils.buildAppComment();
        Long id = expected.getId();
        Mockito.when(commentRepository.findByIsActiveTrueAndId(id)).thenReturn(Optional.of(expected));

        AppComment actual = commentService.findById(id);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenFindById_givenEmptyAppComment_thenReturnAppComment() {
        Long id = RandomUtils.nextLong();
        Mockito.when(commentRepository.findByIsActiveTrueAndId(id)).thenReturn(Optional.empty());
        ResourceNotFoundException expected = new ResourceNotFoundException(Constants.Comment + ", id:" + id);

        try {
            commentService.findById(id);
        } catch (ResourceNotFoundException e) {
            Assertions.assertEquals(expected, e);
        }

    }

    @Test
    void whenGetById_thenReturnCommentDTO() throws ResourceNotFoundException {
        AppComment appComment = ForumTestUtils.buildAppComment();
        CommentDTO expected = ForumTestUtils.buildCommentDTO();
        Long id = appComment.getId();
        Mockito.when(commentRepository.findByIsActiveTrueAndId(id)).thenReturn(Optional.of(appComment));
        Mockito.when(modelMapper.map(appComment, CommentDTO.class)).thenReturn(expected);

        CommentDTO actual = commentService.getById(id);

        Assertions.assertEquals(expected, actual);

    }


    @Test
    void whenCreate_givenValidCommentDTO_thenSuccess() throws SocialNetworkAppException {
        CommentDTO commentDTO = ForumTestUtils.buildCommentDTO();
        AppComment appComment = ForumTestUtils.buildAppComment();
        Post post = appComment.getPost();
        AppUser appUser = AuthTestUtils.buildAppUser(AppRoleName.ROLE_USER);
        Mockito.when(modelMapper.map(commentDTO, AppComment.class)).thenReturn(appComment);
        Mockito.when(userService.getCurrentUser()).thenReturn(appUser);
        Mockito.when(postService.findById(commentDTO.getPostId())).thenReturn(post);
        commentService.create(commentDTO);
    }
}