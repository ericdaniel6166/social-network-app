package com.example.socialnetworkapp.forum.controller;

import com.example.socialnetworkapp.AbstractApiTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.forum.ForumTestUtils;
import com.example.socialnetworkapp.forum.dto.ForumDTO;
import com.example.socialnetworkapp.forum.service.ForumService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ForumApiControllerTest extends AbstractApiTest {

    private static final String URL_TEMPLATE = "/forum";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ForumService forumService;

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
        ForumDTO forumDTO = ForumTestUtils.buildForumDTO();
        Page<ForumDTO> forumDTOPage = new PageImpl<>(Collections.singletonList(forumDTO), pageable, totalElement);

        Mockito.when(forumService.getAll(pageable, search)).thenReturn(forumDTOPage);


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
        Assertions.assertEquals(CommonUtils.writeValueAsString(forumDTOPage), actual.getResponse().getContentAsString());

    }

    @Test
    void whenCreate_givenValidForumDTO_thenReturnOK() throws Exception {
        ForumDTO forumDTO = ForumTestUtils.buildForumDTO();
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        Mockito.when(forumService.create(forumDTO)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(CommonUtils.writeValueAsString(forumDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(CommonUtils.writeValueAsString(simpleResponseDTO), actual.getResponse().getContentAsString());

    }

    @Test
    void whenCreate_givenBlankDescription_thenThrowMethodArgumentNotValidException() throws Exception {
        ForumDTO forumDTO = ForumTestUtils.buildForumDTO();
        forumDTO.setDescription(StringUtils.SPACE);
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        Mockito.when(forumService.create(forumDTO)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(CommonUtils.writeValueAsString(forumDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class,
                Objects.requireNonNull(actual.getResolvedException()));

    }

    @Test
    void whenCreate_givenBlankName_thenThrowMethodArgumentNotValidException() throws Exception {
        ForumDTO forumDTO = ForumTestUtils.buildForumDTO();
        forumDTO.setName(StringUtils.SPACE);
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        Mockito.when(forumService.create(forumDTO)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(CommonUtils.writeValueAsString(forumDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class,
                Objects.requireNonNull(actual.getResolvedException()));

    }


    @Test
    void whenGetById_thenReturnOK() throws Exception {
        ForumDTO forumDTO = ForumTestUtils.buildForumDTO();
        Long id = forumDTO.getId();
        Mockito.when(forumService.getById(id)).thenReturn(forumDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(CommonUtils.writeValueAsString(forumDTO), actual.getResponse().getContentAsString());
    }
}