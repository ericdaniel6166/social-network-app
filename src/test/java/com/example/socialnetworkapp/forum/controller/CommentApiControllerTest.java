package com.example.socialnetworkapp.forum.controller;

import com.example.socialnetworkapp.AbstractApiTest;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.forum.ForumTestUtils;
import com.example.socialnetworkapp.forum.dto.CommentDTO;
import com.example.socialnetworkapp.forum.service.CommentService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Objects;

@WebMvcTest(CommentApiController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentApiControllerTest extends AbstractApiTest {

    private static final String URL_TEMPLATE = "/comment";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentService commentService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenGetAll_thenReturnOK() throws Exception {
        Integer page = Integer.parseInt(Constants.PAGE_REQUEST_PAGE_NUMBER_DEFAULT);
        Integer size = Integer.parseInt(Constants.PAGE_REQUEST_SIZE_DEFAULT);
        Sort.Direction direction = Sort.Direction.DESC;
        String[] properties = ArrayUtils.toArray(Constants.PAGE_REQUEST_PROPERTIES_LAST_MODIFIED_DATE, RandomStringUtils.random(10));
        String search = RandomStringUtils.random(10);

        long totalElement = RandomUtils.nextLong();
        Pageable pageable = CommonUtils.buildPageable(page, size, direction, properties);
        CommentDTO commentDTO = ForumTestUtils.buildCommentDTO();
        Page<CommentDTO> commentDTOPage = new PageImpl<>(Collections.singletonList(commentDTO), pageable, totalElement);

        Mockito.when(commentService.getAll(pageable, search)).thenReturn(commentDTOPage);


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .param("page", page.toString())
                .param("size", size.toString())
                .param("direction", direction.name())
                .param("properties", properties)
                .param("search", search);

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(CommonUtils.writeValueAsString(commentDTOPage), actual.getResponse().getContentAsString());

    }

    @Test
    void whenGetByPostId_thenReturnOK() throws Exception {
        Integer page = Integer.parseInt(Constants.PAGE_REQUEST_PAGE_NUMBER_DEFAULT);
        Integer size = Integer.parseInt(Constants.PAGE_REQUEST_SIZE_DEFAULT);
        Sort.Direction direction = Sort.Direction.DESC;
        String[] properties = ArrayUtils.toArray(Constants.PAGE_REQUEST_PROPERTIES_LAST_MODIFIED_DATE, RandomStringUtils.random(10));

        long totalElement = RandomUtils.nextLong();
        Pageable pageable = CommonUtils.buildPageable(page, size, direction, properties);
        CommentDTO commentDTO = ForumTestUtils.buildCommentDTO();
        Long id = commentDTO.getPostId();
        Page<CommentDTO> commentDTOPage = new PageImpl<>(Collections.singletonList(commentDTO), pageable, totalElement);

        Mockito.when(commentService.getByPostId(id, pageable)).thenReturn(commentDTOPage);


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/post/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .param("page", page.toString())
                .param("size", size.toString())
                .param("direction", direction.name())
                .param("properties", properties);

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(CommonUtils.writeValueAsString(commentDTOPage), actual.getResponse().getContentAsString());

    }

    @Test
    void whenGetById_thenReturnOK() throws Exception {

        CommentDTO commentDTO = ForumTestUtils.buildCommentDTO();
        Long id = commentDTO.getPostId();
        Mockito.when(commentService.getById(id)).thenReturn(commentDTO);


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(CommonUtils.writeValueAsString(commentDTO), actual.getResponse().getContentAsString());

    }

    @Test
    void whenGetByCreatedBy_thenReturnOK() throws Exception {
        Integer page = Integer.parseInt(Constants.PAGE_REQUEST_PAGE_NUMBER_DEFAULT);
        Integer size = Integer.parseInt(Constants.PAGE_REQUEST_SIZE_DEFAULT);
        Sort.Direction direction = Sort.Direction.DESC;
        String[] properties = ArrayUtils.toArray(Constants.PAGE_REQUEST_PROPERTIES_LAST_MODIFIED_DATE, RandomStringUtils.random(10));

        long totalElement = RandomUtils.nextLong();
        Pageable pageable = CommonUtils.buildPageable(page, size, direction, properties);
        CommentDTO commentDTO = ForumTestUtils.buildCommentDTO();
        String username = AuthTestUtils.USERNAME;
        Page<CommentDTO> commentDTOPage = new PageImpl<>(Collections.singletonList(commentDTO), pageable, totalElement);

        Mockito.when(commentService.getByCreatedBy(username, pageable)).thenReturn(commentDTOPage);


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/createdBy/" + username)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .param("page", page.toString())
                .param("size", size.toString())
                .param("direction", direction.name())
                .param("properties", properties);

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(CommonUtils.writeValueAsString(commentDTOPage), actual.getResponse().getContentAsString());

    }


    @Test
    void whenCreate_givenValidCommentDTO_thenReturnOK() throws Exception {
        CommentDTO commentDTO = ForumTestUtils.buildCommentDTO();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(CommonUtils.writeValueAsString(commentDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());

    }

    @Test
    void whenDeleteById_thenReturnOK() throws Exception {
        long id = RandomUtils.nextLong();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete(URL_TEMPLATE + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());

    }

    @Test
    void whenCreate_givenBlankContent_thenThrowMethodArgumentNotValidException() throws Exception {
        CommentDTO commentDTO = ForumTestUtils.buildCommentDTO();
        commentDTO.setContent(StringUtils.SPACE);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(CommonUtils.writeValueAsString(commentDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class,
                Objects.requireNonNull(actual.getResolvedException()));

    }

}