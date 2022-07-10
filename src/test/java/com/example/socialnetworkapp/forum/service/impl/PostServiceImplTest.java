package com.example.socialnetworkapp.forum.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.auth.AuthTestUtils;
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
import com.example.socialnetworkapp.forum.service.ForumService;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.service.MasterMessageService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
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

        Page<PostDTO> actual = postService.findAll(pageable, search);

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
        Mockito.when(postRepository.findAll(pageable)).thenReturn(postPage);
        Mockito.when(modelMapper.map(post, PostDTO.class)).thenReturn(postDTO);

        Page<PostDTO> actual = postService.findAll(pageable, search);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenCreate_givenValidPostDTO_thenReturnSimpleResponseDTO() throws SocialNetworkAppException {
        PostDTO postDTO = ForumTestUtils.buildPostDTO();
        Post post = ForumTestUtils.buildPost();
        Mockito.when(modelMapper.map(postDTO, Post.class)).thenReturn(post);
        AppUser appUser = AuthTestUtils.buildAppUser();
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
    void whenFindById_givenNotEmptyPost_thenReturnForum() throws SocialNetworkAppException {
        Post expected = ForumTestUtils.buildPost();
        Long id = expected.getId();
        Mockito.when(postRepository.findById(id)).thenReturn(Optional.of(expected));

        Post actual = postService.findById(id);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenFindById_givenEmptyPost_thenThrowResourceNotFoundException() {
        Long id = RandomUtils.nextLong();
        Mockito.when(postRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException expected = new ResourceNotFoundException(Constants.POST + ", id:" + id);

        try {
            postService.findById(id);
        } catch (ResourceNotFoundException e) {
            Assertions.assertEquals(expected, e);
        }


    }

}