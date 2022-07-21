package com.example.socialnetworkapp.forum.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.enums.RoleEnum;
import com.example.socialnetworkapp.auth.model.AppUser;
import com.example.socialnetworkapp.auth.service.UserService;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.forum.ForumTestUtils;
import com.example.socialnetworkapp.forum.dto.ForumDTO;
import com.example.socialnetworkapp.forum.model.AppComment;
import com.example.socialnetworkapp.forum.model.Forum;
import com.example.socialnetworkapp.forum.model.Post;
import com.example.socialnetworkapp.forum.repository.ForumRepository;
import com.example.socialnetworkapp.forum.service.CommentService;
import com.example.socialnetworkapp.forum.service.PostService;
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

import java.util.Collections;
import java.util.Optional;

class ForumServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private ForumServiceImpl forumService;

    @Mock
    private MasterMessageService masterMessageService;

    @Mock
    private ForumRepository forumRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @Mock
    private CommentService commentService;

    @Mock
    private PostService postService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenSaveAndFlush_givenForum_thenReturnForum() {
        Forum expected = ForumTestUtils.buildForum();
        Mockito.when(forumRepository.saveAndFlush(expected)).thenReturn(expected);

        Forum actual = forumService.saveAndFlush(expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenExistsById_thenReturnTrue() {
        Long id = RandomUtils.nextLong();
        Mockito.when(forumRepository.existsById(id)).thenReturn(true);

        boolean actual = forumService.existsById(id);

        Assertions.assertTrue(actual);
    }

    @Test
    void whenDeleteById_thenReturnSimpleResponseDTO() throws SocialNetworkAppException {
        AppComment appComment = ForumTestUtils.buildAppComment();
        Post post = ForumTestUtils.buildPost();
        post.setCommentList(Collections.singletonList(appComment));
        Forum forum = ForumTestUtils.buildForum();
        forum.setPostList(Collections.singletonList(post));
        Long id = forum.getId();

        AppComment appCommentReturn = ForumTestUtils.buildAppComment();
        appCommentReturn.setIsActive(false);
        Post postReturn = ForumTestUtils.buildPost();
        postReturn.setIsActive(false);
        postReturn.setCommentList(Collections.singletonList(appComment));
        Forum forumReturn = ForumTestUtils.buildForum();
        forumReturn.setIsActive(false);
        forumReturn.setPostList(Collections.singletonList(post));

        Mockito.when(forumRepository.findByIsActiveTrueAndId(id)).thenReturn(Optional.of(forum));
        Mockito.when(forumRepository.saveAndFlush(forumReturn)).thenReturn(forumReturn);
        Mockito.when(postService.setIsActiveList(forumReturn.getPostList(), false)).thenReturn(Collections.singletonList(postReturn));
        Mockito.when(commentService.setIsActiveList(postReturn.getCommentList(), false)).thenReturn(Collections.singletonList(appCommentReturn));
        MasterMessage masterMessage = CommonTestUtils.buildMasterMessage(MasterMessageCode.DELETE_SUCCESS);
        Mockito.when(masterMessageService.findByMessageCode(MasterMessageCode.DELETE_SUCCESS)).thenReturn(masterMessage);
        String title = CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getTitle()),
                Constants.FORUM.toUpperCase()
        );
        String message = CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getMessage()),
                Constants.FORUM.toLowerCase(),
                post.getName()
        );
        SimpleResponseDTO expected = new SimpleResponseDTO(title, message);

        SimpleResponseDTO actual = forumService.deleteById(id);

        Assertions.assertEquals(expected, actual);

    }





    @Test
    void whenFindAll_givenNotBlankSearch_thenReturnForumDTOPage() throws SocialNetworkAppException {
        String search = CommonTestUtils.SEARCH;
        ForumDTO forumDTO = ForumTestUtils.buildForumDTO();
        Forum forum = ForumTestUtils.buildForum();
        Pageable pageable = CommonTestUtils.buildPageable();
        Page<Forum> forumPage = (Page<Forum>) CommonTestUtils.buildPage(forum, forum);
        Page<ForumDTO> expected = (Page<ForumDTO>) CommonTestUtils.buildPage(forumDTO, forumDTO);
        Mockito.when(forumRepository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable))).thenReturn(forumPage);
        Mockito.when(modelMapper.map(forum, ForumDTO.class)).thenReturn(forumDTO);

        Page<ForumDTO> actual = forumService.getAll(pageable, search);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenFindAll_givenBlankSearch_thenReturnForumDTOPage() throws SocialNetworkAppException {
        String search = StringUtils.SPACE;
        ForumDTO forumDTO = ForumTestUtils.buildForumDTO();
        Forum forum = ForumTestUtils.buildForum();
        Pageable pageable = CommonTestUtils.buildPageable();
        Page<Forum> forumPage = (Page<Forum>) CommonTestUtils.buildPage(forum, forum);
        Page<ForumDTO> expected = (Page<ForumDTO>) CommonTestUtils.buildPage(forumDTO, forumDTO);
        Mockito.when(forumRepository.findAllByIsActiveTrue(pageable)).thenReturn(forumPage);
        Mockito.when(modelMapper.map(forum, ForumDTO.class)).thenReturn(forumDTO);

        Page<ForumDTO> actual = forumService.getAll(pageable, search);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenCreate_givenValidForumDTO_thenReturnSimpleResponseDTO() throws SocialNetworkAppException {
        ForumDTO forumDTO = ForumTestUtils.buildForumDTO();
        Forum forum = ForumTestUtils.buildForum();
        Mockito.when(modelMapper.map(forumDTO, Forum.class)).thenReturn(forum);
        AppUser appUser = AuthTestUtils.buildAppUser(RoleEnum.ROLE_USER);
        Mockito.when(userService.getCurrentUser()).thenReturn(appUser);
        MasterMessage masterMessage = CommonTestUtils.buildMasterMessage(MasterMessageCode.CREATE_SUCCESS);
        Mockito.when(masterMessageService.findByMessageCode(MasterMessageCode.CREATE_SUCCESS)).thenReturn(masterMessage);
        String title = CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getTitle()),
                Constants.FORUM.toUpperCase()
        );
        String message = CommonUtils.formatString(
                StringEscapeUtils.unescapeJava(masterMessage.getMessage()),
                Constants.FORUM.toLowerCase(),
                forum.getName()
        );
        SimpleResponseDTO expected = new SimpleResponseDTO(title, message);

        SimpleResponseDTO actual = forumService.create(forumDTO);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenFindById_givenNotEmptyForum_thenReturnForum() throws SocialNetworkAppException {
        Forum expected = ForumTestUtils.buildForum();
        Long id = expected.getId();
        Mockito.when(forumRepository.findByIsActiveTrueAndId(id)).thenReturn(Optional.of(expected));

        Forum actual = forumService.findById(id);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenFindById_givenEmptyForum_thenThrowResourceNotFoundException() {
        Long id = RandomUtils.nextLong();
        Mockito.when(forumRepository.findByIsActiveTrueAndId(id)).thenReturn(Optional.empty());
        ResourceNotFoundException expected = new ResourceNotFoundException(Constants.FORUM + ", id:" + id);

        try {
            forumService.findById(id);
        } catch (ResourceNotFoundException e) {
            Assertions.assertEquals(expected, e);
        }


    }

    @Test
    void whenGetById_thenReturnForumDTO() throws ResourceNotFoundException {
        Forum forum = ForumTestUtils.buildForum();
        ForumDTO expected = ForumTestUtils.buildForumDTO();
        Long id = forum.getId();
        Mockito.when(forumRepository.findByIsActiveTrueAndId(id)).thenReturn(Optional.of(forum));
        Mockito.when(modelMapper.map(forum, ForumDTO.class)).thenReturn(expected);

        ForumDTO actual = forumService.getById(id);

        Assertions.assertEquals(expected, actual);

    }
}