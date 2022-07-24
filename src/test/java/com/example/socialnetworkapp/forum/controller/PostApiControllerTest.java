package com.example.socialnetworkapp.forum.controller;

import com.example.socialnetworkapp.AbstractApiTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.forum.ForumTestUtils;
import com.example.socialnetworkapp.forum.dto.PostDTO;
import com.example.socialnetworkapp.forum.service.PostService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class PostApiControllerTest extends AbstractApiTest {

    private static final String URL_TEMPLATE = "/post";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenCreate_givenValidPostDTO_thenReturnOK() throws Exception {
        PostDTO postDTO = ForumTestUtils.buildPostDTO();
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        Mockito.when(postService.create(postDTO)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(postDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(simpleResponseDTO), actual.getResponse().getContentAsString());

    }

    @Test
    void whenDeleteById_thenReturnOK() throws Exception {
        Long id = RandomUtils.nextLong();
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        Mockito.when(postService.deleteById(id)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete(URL_TEMPLATE + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(simpleResponseDTO), actual.getResponse().getContentAsString());

    }


    @Test
    void whenCreate_givenBlankName_thenThrowMethodArgumentNotValidException() throws Exception {
        PostDTO postDTO = ForumTestUtils.buildPostDTO();
        postDTO.setName(StringUtils.SPACE);
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        Mockito.when(postService.create(postDTO)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(postDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class,
                Objects.requireNonNull(actual.getResolvedException()));
    }

    @Test
    void whenCreate_givenBlankContent_thenThrowMethodArgumentNotValidException() throws Exception {
        PostDTO postDTO = ForumTestUtils.buildPostDTO();
        postDTO.setContent(StringUtils.SPACE);
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        Mockito.when(postService.create(postDTO)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(postDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class,
                Objects.requireNonNull(actual.getResolvedException()));
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
        PostDTO forumDTO = ForumTestUtils.buildPostDTO();
        Page<PostDTO> postDTOPage = new PageImpl<>(Collections.singletonList(forumDTO), pageable, totalElement);

        Mockito.when(postService.getAll(pageable, search)).thenReturn(postDTOPage);


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
        Assertions.assertEquals(objectMapper.writeValueAsString(postDTOPage), actual.getResponse().getContentAsString());

    }

    @Test
    void whenGetById_thenReturnOK() throws Exception {
        PostDTO postDTO = ForumTestUtils.buildPostDTO();
        Long id = RandomUtils.nextLong();
        Mockito.when(postService.getById(id)).thenReturn(postDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(postDTO), actual.getResponse().getContentAsString());
    }

    @Test
    void whenGetByForumId_thenReturnOK() throws Exception {
        Integer page = Integer.parseInt(Constants.PAGE_REQUEST_PAGE_NUMBER_DEFAULT);
        Integer size = Integer.parseInt(Constants.PAGE_REQUEST_SIZE_DEFAULT);
        Sort.Direction direction = Sort.Direction.DESC;
        String[] properties = ArrayUtils.toArray(Constants.PAGE_REQUEST_PROPERTIES_LAST_MODIFIED_DATE, RandomStringUtils.random(10));
        long totalElement = RandomUtils.nextLong();
        PostDTO postDTO = ForumTestUtils.buildPostDTO();
        Long id = postDTO.getForumId();
        Pageable pageable = CommonUtils.buildPageable(page, size, direction, properties);
        Page<PostDTO> postDTOPage = new PageImpl<>(Collections.singletonList(postDTO), pageable, totalElement);
        Mockito.when(postService.getByForumId(id, pageable)).thenReturn(postDTOPage);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/forum/" + id)
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
        Assertions.assertEquals(objectMapper.writeValueAsString(postDTOPage), actual.getResponse().getContentAsString());
    }

    @Test
    void whenGetByCreatedBy_thenReturnOK() throws Exception {
        Integer page = Integer.parseInt(Constants.PAGE_REQUEST_PAGE_NUMBER_DEFAULT);
        Integer size = Integer.parseInt(Constants.PAGE_REQUEST_SIZE_DEFAULT);
        Sort.Direction direction = Sort.Direction.DESC;
        String[] properties = ArrayUtils.toArray(Constants.PAGE_REQUEST_PROPERTIES_LAST_MODIFIED_DATE, RandomStringUtils.random(10));
        long totalElement = RandomUtils.nextLong();
        PostDTO postDTO = ForumTestUtils.buildPostDTO();
        String createdBy = AuthTestUtils.USERNAME;
        Pageable pageable = CommonUtils.buildPageable(page, size, direction, properties);
        Page<PostDTO> postDTOPage = new PageImpl<>(Collections.singletonList(postDTO), pageable, totalElement);
        Mockito.when(postService.getByUsername(createdBy, pageable)).thenReturn(postDTOPage);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/username/" + createdBy)
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
        Assertions.assertEquals(objectMapper.writeValueAsString(postDTOPage), actual.getResponse().getContentAsString());
    }


}