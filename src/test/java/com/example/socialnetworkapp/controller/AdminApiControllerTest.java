package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.AbstractApiTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.dto.MasterErrorMessageDTO;
import com.example.socialnetworkapp.dto.MasterMessageDTO;
import com.example.socialnetworkapp.service.MasterErrorMessageService;
import com.example.socialnetworkapp.service.MasterMessageService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

@WebMvcTest(AdminApiController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdminApiControllerTest extends AbstractApiTest {

    private static final String URL_TEMPLATE = "/admin";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MasterMessageService masterMessageService;

    @Autowired
    private MasterErrorMessageService masterErrorMessageService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenFindAllMasterErrorMessage_thenReturnOK() throws Exception {
        List<MasterErrorMessageDTO> masterErrorMessageDTOList = Collections.singletonList(CommonTestUtils.buildMasterErrorMessageDTO(null));
        Mockito.when(masterErrorMessageService.findAll()).thenReturn(masterErrorMessageDTOList);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/masterErrorMessage")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(masterErrorMessageDTOList), actual.getResponse().getContentAsString());

    }

    @Test
    void whenFindAllMasterMessage_thenReturnOK() throws Exception {
        List<MasterMessageDTO> masterMessageDTOList = Collections.singletonList(CommonTestUtils.buildMasterMessageDTO(null));
        Mockito.when(masterMessageService.findAll()).thenReturn(masterMessageDTOList);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/masterMessage")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(masterMessageDTOList), actual.getResponse().getContentAsString());

    }
}