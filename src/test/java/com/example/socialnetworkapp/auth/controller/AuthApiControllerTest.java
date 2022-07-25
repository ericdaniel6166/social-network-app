package com.example.socialnetworkapp.auth.controller;

import com.example.socialnetworkapp.AbstractApiTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.dto.AuthenticationResponseDTO;
import com.example.socialnetworkapp.auth.dto.RefreshTokenRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignInRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignUpRequestDTO;
import com.example.socialnetworkapp.auth.service.AuthService;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

@WebMvcTest(AuthApiController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthApiControllerTest extends AbstractApiTest {

    private static final String URL_TEMPLATE = "/auth";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenSignUp_givenValidSignUpRequestDTO_thenReturnOK() throws Exception {
        SignUpRequestDTO signUpRequestDTO = AuthTestUtils.buildSignUpRequestDTO();
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        Mockito.when(authService.signUp(signUpRequestDTO)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE + "/signUp")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(signUpRequestDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(simpleResponseDTO), actual.getResponse().getContentAsString());

    }

    @Test
    void whenSignUp_givenWrongEmailFormat_thenThrowMethodArgumentNotValidException() throws Exception {
        SignUpRequestDTO signUpRequestDTO = AuthTestUtils.buildSignUpRequestDTO();
        signUpRequestDTO.setEmail("wrongEmailFormat");
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        Mockito.when(authService.signUp(signUpRequestDTO)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE + "/signUp")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(signUpRequestDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class,
                Objects.requireNonNull(actual.getResolvedException()));

    }

    @Test
    void whenSignUp_givenBlankEmail_thenThrowMethodArgumentNotValidException() throws Exception {
        SignUpRequestDTO signUpRequestDTO = AuthTestUtils.buildSignUpRequestDTO();
        signUpRequestDTO.setEmail(StringUtils.SPACE);
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        Mockito.when(authService.signUp(signUpRequestDTO)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE + "/signUp")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(signUpRequestDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class,
                Objects.requireNonNull(actual.getResolvedException()));

    }

    @Test
    void whenSignUp_givenBlankUsername_thenThrowMethodArgumentNotValidException() throws Exception {
        SignUpRequestDTO signUpRequestDTO = AuthTestUtils.buildSignUpRequestDTO();
        signUpRequestDTO.setUsername(StringUtils.SPACE);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE + "/signUp")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(signUpRequestDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class,
                Objects.requireNonNull(actual.getResolvedException()));

    }

    @Test
    void whenSignUp_givenBlankPassword_thenThrowMethodArgumentNotValidException() throws Exception {
        SignUpRequestDTO signUpRequestDTO = AuthTestUtils.buildSignUpRequestDTO();
        signUpRequestDTO.setPassword(StringUtils.SPACE);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE + "/signUp")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(signUpRequestDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class,
                Objects.requireNonNull(actual.getResolvedException()));

    }

    @Test
    void whenVerifyAccount_givenValidToken_thenReturnOK() throws Exception {
        String token = RandomStringUtils.randomAlphabetic(10);
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        Mockito.when(authService.verifyAccount(token)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(URL_TEMPLATE + "/verifyAccount/" + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8);

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(simpleResponseDTO), actual.getResponse().getContentAsString());

    }

    @Test
    void whenSignIn_givenValidSignInRequestDTO_thenReturnOK() throws Exception {
        SignInRequestDTO signInRequestDTO = AuthTestUtils.buildSignInRequestDTO();
        AuthenticationResponseDTO authenticationResponseDTO = AuthTestUtils.buildSignInResponseDTO();
        Mockito.when(authService.signIn(signInRequestDTO)).thenReturn(authenticationResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE + "/signIn")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(signInRequestDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(authenticationResponseDTO), actual.getResponse().getContentAsString());

    }

    @Test
    void whenSignIn_givenBlankUsername_thenThrowMethodArgumentNotValidException() throws Exception {
        SignInRequestDTO signInRequestDTO = AuthTestUtils.buildSignInRequestDTO();
        signInRequestDTO.setUsername(StringUtils.SPACE);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE + "/signIn")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(signInRequestDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class,
                Objects.requireNonNull(actual.getResolvedException()));

    }

    @Test
    void whenSignIn_givenBlankPassword_thenThrowMethodArgumentNotValidException() throws Exception {
        SignInRequestDTO signInRequestDTO = AuthTestUtils.buildSignInRequestDTO();
        signInRequestDTO.setPassword(StringUtils.SPACE);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE + "/signIn")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(signInRequestDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertInstanceOf(MethodArgumentNotValidException.class,
                Objects.requireNonNull(actual.getResolvedException()));

    }

    @Test
    void whenRefreshToken_givenValidRefreshTokenRequestDTO_thenReturnOK() throws Exception {
        RefreshTokenRequestDTO refreshTokenRequestDTO = AuthTestUtils.buildRefreshTokenRequestDTO();
        AuthenticationResponseDTO authenticationResponseDTO = AuthTestUtils.buildSignInResponseDTO();
        Mockito.when(authService.refreshToken(refreshTokenRequestDTO)).thenReturn(authenticationResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE + "/refreshToken")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(refreshTokenRequestDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(authenticationResponseDTO), actual.getResponse().getContentAsString());

    }

    @Test
    void whenSignOut_givenValidRefreshTokenRequestDTO_thenReturnOK() throws Exception {
        RefreshTokenRequestDTO refreshTokenRequestDTO = AuthTestUtils.buildRefreshTokenRequestDTO();
        SimpleResponseDTO simpleResponseDTO = CommonTestUtils.buildSimpleResponseDTO();
        Mockito.when(authService.signOut(refreshTokenRequestDTO)).thenReturn(simpleResponseDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(URL_TEMPLATE + "/signOut")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8)
                .content(objectMapper.writeValueAsString(refreshTokenRequestDTO));

        MvcResult actual = mockMvc.perform(builder)
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        Assertions.assertEquals(objectMapper.writeValueAsString(simpleResponseDTO), actual.getResponse().getContentAsString());

    }


}