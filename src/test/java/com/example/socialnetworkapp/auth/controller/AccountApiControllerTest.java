package com.example.socialnetworkapp.auth.controller;

import com.example.socialnetworkapp.AbstractApiTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.dto.UserProfileInfoDTO;
import com.example.socialnetworkapp.auth.dto.UserRoleUpdateRequestDTO;
import com.example.socialnetworkapp.auth.enums.RoleEnum;
import com.example.socialnetworkapp.auth.service.AccountService;
import com.example.socialnetworkapp.configuration.AppConfiguration;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
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

@WebMvcTest(AccountApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountApiControllerTest extends AbstractApiTest {

    private static final String URL_TEMPLATE = "/account";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AppConfiguration appConfiguration;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenUpdateRole_thenReturnSimpleResponseDTO() throws Exception {
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        UserRoleUpdateRequestDTO userRoleUpdateRequestDTO = AuthTestUtils.buildUserRoleUpdateRequestDTO(RoleEnum.ROLE_USER);
        Mockito.when(accountService.updateRole(userRoleUpdateRequestDTO)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put(URL_TEMPLATE + "/updateRole")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(userRoleUpdateRequestDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(simpleResponseDTO), actual.getResponse().getContentAsString());

    }

    @Test
    void whenCreateOrUpdateUserProfileInfo_thenReturnSimpleResponseDTO() throws Exception {
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        String username = AuthTestUtils.USERNAME;
        UserProfileInfoDTO userProfileInfoDTO = AuthTestUtils.buildUserProfileInfoRequestDTO();
        Mockito.when(appConfiguration.getAgeMaximum()).thenReturn(130L);
        Mockito.when(appConfiguration.getAgeMinimum()).thenReturn(13L);
        Mockito.when(accountService.createOrUpdateUserProfileInfo(username, userProfileInfoDTO)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE + "/profile/" + username)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(userProfileInfoDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(simpleResponseDTO), actual.getResponse().getContentAsString());

    }

    @Test
    void whenGetUserProfileInfoByUsername_thenReturnUserProfileInfoDTO() throws Exception {
        String username = AuthTestUtils.USERNAME;
        UserProfileInfoDTO userProfileInfoDTO = AuthTestUtils.buildUserProfileInfoRequestDTO();
        Mockito.when(accountService.getUserProfileInfoByUsername(username)).thenReturn(userProfileInfoDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/profile/" + username)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(userProfileInfoDTO), actual.getResponse().getContentAsString());

    }


}