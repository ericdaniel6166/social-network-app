package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.AbstractApiTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.dto.MasterErrorMessageDTO;
import com.example.socialnetworkapp.dto.MasterMessageDTO;
import com.example.socialnetworkapp.service.MasterErrorMessageService;
import com.example.socialnetworkapp.service.MasterMessageService;
import com.example.socialnetworkapp.utils.CommonUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
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
@ComponentScan(value = "com.example.socialnetworkapp.controller")
class AdminApiControllerTest extends AbstractApiTest {

    private static final String URL_TEMPLATE = "/admin";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MasterMessageService masterMessageService;

    @MockBean
    private MasterErrorMessageService masterErrorMessageService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenFindAllMasterErrorMessage_thenReturnOK() throws Exception {
        List<MasterErrorMessageDTO> masterErrorMessageDTOList = Collections.singletonList(CommonTestUtils.buildMasterErrorMessageDTO());
        Mockito.when(masterErrorMessageService.findAll()).thenReturn(masterErrorMessageDTOList);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/masterErrorMessage")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult result = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assertions.assertEquals(CommonUtils.writeValueAsString(masterErrorMessageDTOList), result.getResponse().getContentAsString());

    }

    @Test
    void whenFindAllMasterMessage_thenReturnOK() throws Exception {
        List<MasterMessageDTO> masterErrorMessageDTOList = Collections.singletonList(CommonTestUtils.buildMasterMessageDTO());
        Mockito.when(masterMessageService.findAll()).thenReturn(masterErrorMessageDTOList);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/masterMessage")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult result = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assertions.assertEquals(CommonUtils.writeValueAsString(masterErrorMessageDTOList), result.getResponse().getContentAsString());

    }
}