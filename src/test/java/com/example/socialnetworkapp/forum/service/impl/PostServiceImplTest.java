package com.example.socialnetworkapp.forum.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.enums.AppRoleName;
import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.ForumTestUtils;
import com.example.socialnetworkapp.forum.dto.PostDTO;
import com.example.socialnetworkapp.forum.model.Post;
import com.example.socialnetworkapp.forum.repository.PostRepository;
import com.example.socialnetworkapp.forum.service.CommentService;
import com.example.socialnetworkapp.forum.service.ForumService;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.service.MasterMessageService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
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

class PostServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private MasterMessageService masterMessageService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @Mock
    private ForumService forumService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenSaveAndFlush_givenForum_thenReturnForum() {
        Post expected = ForumTestUtils.buildPost();
        Mockito.when(postRepository.saveAndFlush(expected)).thenReturn(expected);

        Post actual = postService.saveAndFlush(expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenFindAll_givenNotBlankSearch_thenReturnForumDTOPage() throws SocialNetworkAppException {
        String search = CommonTestUtils.SEARCH;
        PostDTO postDTO = ForumTestUtils.buildPostDTO();
        Post post = ForumTestUtils.buildPost();
        Pageable pageable = CommonTestUtils.buildPageable();
        Page<Post> postPage = (Page<Post>) CommonTestUtils.buildPage(post, post);
        Page<PostDTO> expected = (Page<PostDTO>) CommonTestUtils.buildPage(postDTO, postDTO);
        Mockito.when(postRepository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable))).thenReturn(postPage);
        Mockito.when(modelMapper.map(post, PostDTO.class)).thenReturn(postDTO);

        Page<PostDTO> actual = postService.getAll(pageable, search);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenFindAll_givenBlankSearch_thenReturnForumDTOPage() throws SocialNetworkAppException {
        String search = StringUtils.SPACE;
        PostDTO postDTO = ForumTestUtils.buildPostDTO();
        Post post = ForumTestUtils.buildPost();
        Pageable pageable = CommonTestUtils.buildPageable();
        Page<Post> postPage = (Page<Post>) CommonTestUtils.buildPage(post, post);
        Page<PostDTO> expected = (Page<PostDTO>) CommonTestUtils.buildPage(postDTO, postDTO);
        Mockito.when(postRepository.findAllByIsActiveTrue(pageable)).thenReturn(postPage);
        Mockito.when(modelMapper.map(post, PostDTO.class)).thenReturn(postDTO);

        Page<PostDTO> actual = postService.getAll(pageable, search);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenCreate_givenValidPostDTO_thenReturnSimpleResponseDTO() throws SocialNetworkAppException {
        PostDTO postDTO = ForumTestUtils.buildPostDTO();
        Post post = ForumTestUtils.buildPost();
        Mockito.when(modelMapper.map(postDTO, Post.class)).thenReturn(post);
        AppUser appUser = AuthTestUtils.buildAppUser(AppRoleName.ROLE_USER);
        Mockito.when(userService.getCurrentUser()).thenReturn(appUser);
        Mockito.when(forumService.findById(postDTO.getForumId())).thenReturn(post.getForum());
        MasterMessage masterMessage = CommonTestUtils.buildMasterMessage(MasterMessageCode.CREATE_SUCCESS);
        Mockito.when(masterMessageService.findByMessageCode(MasterMessageCode.CREATE_SUCCESS)).thenReturn(masterMessage);
        String title = CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getTitle()),
                Constants.POST.toUpperCase()
        );
        String message = CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getMessage()),
                Constants.POST.toLowerCase(),
                post.getName()
        );
        SimpleResponseDTO expected = new SimpleResponseDTO(title, message);

        SimpleResponseDTO actual = postService.create(postDTO);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenDeleteById_thenReturnSimpleResponseDTO() throws SocialNetworkAppException {
        Post post = ForumTestUtils.buildPost();
        Long id = post.getId();
        Mockito.when(postRepository.findByIsActiveTrueAndId(id)).thenReturn(Optional.of(post));
        MasterMessage masterMessage = CommonTestUtils.buildMasterMessage(MasterMessageCode.DELETE_SUCCESS);
        Mockito.when(masterMessageService.findByMessageCode(MasterMessageCode.DELETE_SUCCESS)).thenReturn(masterMessage);
        String title = CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getTitle()),
                Constants.POST.toUpperCase()
        );
        String message = CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getMessage()),
                Constants.POST.toLowerCase(),
                post.getName()
        );
        SimpleResponseDTO expected = new SimpleResponseDTO(title, message);

        SimpleResponseDTO actual = postService.deleteById(id);

        Assertions.assertEquals(expected, actual);

    }



    @Test
    void whenFindById_givenNotEmptyPost_thenReturnForum() throws SocialNetworkAppException {
        Post expected = ForumTestUtils.buildPost();
        Long id = expected.getId();
        Mockito.when(postRepository.findByIsActiveTrueAndId(id)).thenReturn(Optional.of(expected));

        Post actual = postService.findById(id);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenFindById_givenEmptyPost_thenThrowResourceNotFoundException() {
        Long id = RandomUtils.nextLong();
        Mockito.when(postRepository.findByIsActiveTrueAndId(id)).thenReturn(Optional.empty());
        ResourceNotFoundException expected = new ResourceNotFoundException(Constants.POST + ", id:" + id);

        try {
            postService.findById(id);
        } catch (ResourceNotFoundException e) {
            Assertions.assertEquals(expected, e);
        }


    }

    @Test
    void whenGetById_thenReturnForumDTO() throws ResourceNotFoundException {
        Post post = ForumTestUtils.buildPost();
        PostDTO expected = ForumTestUtils.buildPostDTO();
        Long id = post.getId();
        Mockito.when(postRepository.findByIsActiveTrueAndId(id)).thenReturn(Optional.of(post));
        Mockito.when(modelMapper.map(post, PostDTO.class)).thenReturn(expected);

        PostDTO actual = postService.getById(id);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenGetByForumId_givenForumExists_thenReturnForumDTOPage() throws ResourceNotFoundException {
        Post post = ForumTestUtils.buildPost();
        PostDTO postDTO = ForumTestUtils.buildPostDTO();
        Long id = postDTO.getForumId();
        Pageable pageable = CommonTestUtils.buildPageable();
        Page<Post> postPage = (Page<Post>) CommonTestUtils.buildPage(post, post);
        Page<PostDTO> expected = (Page<PostDTO>) CommonTestUtils.buildPage(postDTO, postDTO);

        Mockito.when(postRepository.findAllByIsActiveTrueAndForum_Id(id, pageable)).thenReturn(postPage);
        Mockito.when(forumService.existsById(id)).thenReturn(true);
        Mockito.when(modelMapper.map(post, PostDTO.class)).thenReturn(postDTO);

        Page<PostDTO> actual = postService.getByForumId(id, pageable);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenGetByForumId_givenForumNotExists_thenThrowResourceNotFoundException() {
        Long id = RandomUtils.nextLong();
        Pageable pageable = CommonTestUtils.buildPageable();

        Mockito.when(forumService.existsById(id)).thenReturn(false);
        ResourceNotFoundException expected = new ResourceNotFoundException(Constants.FORUM + ", id:" + id);

        try {
            postService.getByForumId(id, pageable);
        } catch (ResourceNotFoundException e) {
            Assertions.assertEquals(expected, e);
        }

    }


    @Test
    void whenGetByCreatedBy_givenUsernameExists_thenReturnForumDTOPage() throws ResourceNotFoundException {
        Post post = ForumTestUtils.buildPost();
        PostDTO postDTO = ForumTestUtils.buildPostDTO();
        String username = AuthTestUtils.USERNAME;
        Pageable pageable = CommonTestUtils.buildPageable();
        Page<Post> postPage = (Page<Post>) CommonTestUtils.buildPage(post, post);
        Page<PostDTO> expected = (Page<PostDTO>) CommonTestUtils.buildPage(postDTO, postDTO);

        Mockito.when(userService.existsByUsername(username)).thenReturn(true);
        Mockito.when(postRepository.findAllByIsActiveTrueAndCreatedBy(username, pageable)).thenReturn(postPage);
        Mockito.when(modelMapper.map(post, PostDTO.class)).thenReturn(postDTO);

        Page<PostDTO> actual = postService.getByCreatedBy(username, pageable);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenGetByCreatedBy_givenUsernameNotExists_thenThrowResourceNotFoundException() {
        String username = RandomStringUtils.random(10);
        Pageable pageable = CommonTestUtils.buildPageable();

        Mockito.when(userService.existsByUsername(username)).thenReturn(false);
        ResourceNotFoundException expected = new ResourceNotFoundException("username " + username);

        try {
            postService.getByCreatedBy(username, pageable);
        } catch (ResourceNotFoundException e) {
            Assertions.assertEquals(expected, e);
        }

    }

    @Test
    void whenExistsById_thenReturnTrue() {
        Long id = RandomUtils.nextLong();
        Mockito.when(postRepository.existsById(id)).thenReturn(true);

        boolean actual = postService.existsById(id);

        Assertions.assertTrue(actual);

    }


}