package com.example.socialnetworkapp.auth.controller;

import com.example.socialnetworkapp.AbstractApiTest;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.dto.SignInRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignInResponseDTO;
import com.example.socialnetworkapp.auth.dto.SignUpRequestDTO;
import com.example.socialnetworkapp.auth.service.AuthService;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.utils.CommonUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(AuthApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthApiControllerTest extends AbstractApiTest {
    private static final String URL_TEMPLATE = "/auth";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenSignUp_givenValidSignUpRequestDTO_thenReturnOK() throws Exception {
        SignUpRequestDTO signUpRequestDTO = AuthTestUtils.buildSignUpRequestDTO();
        SimpleResponseDTO simpleResponseDTO = AuthTestUtils.buildSimpleResponseDTO();
        Mockito.when(authService.signUp(signUpRequestDTO)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE + "/signUp")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(CommonUtils.writeValueAsString(signUpRequestDTO));

        MvcResult result = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assertions.assertEquals(CommonUtils.writeValueAsString(simpleResponseDTO), result.getResponse().getContentAsString());

    }


    @Test
    void whenVerifyAccount_givenValidToken_thenReturnOK() throws Exception {
        String token = RandomStringUtils.randomAlphabetic(10);
        SimpleResponseDTO simpleResponseDTO = AuthTestUtils.buildSimpleResponseDTO();
        Mockito.when(authService.verifyAccount(token)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/verifyAccount/" + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult result = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assertions.assertEquals(CommonUtils.writeValueAsString(simpleResponseDTO), result.getResponse().getContentAsString());

    }

    @Test
    void whenSignIn_givenValidSignInRequestDTO_thenReturnOK() throws Exception {
        SignInRequestDTO signInRequestDTO = AuthTestUtils.buildSignInRequestDTO();
        SignInResponseDTO signInResponseDTO = AuthTestUtils.buildSignInResponseDTO();
        Mockito.when(authService.signIn(signInRequestDTO)).thenReturn(signInResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE + "/signIn")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(CommonUtils.writeValueAsString(signInRequestDTO));

        MvcResult result = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assertions.assertEquals(CommonUtils.writeValueAsString(signInResponseDTO), result.getResponse().getContentAsString());

    }
}