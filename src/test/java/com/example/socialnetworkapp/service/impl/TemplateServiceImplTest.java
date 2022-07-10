package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.CommonTestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TemplateServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private TemplateServiceImpl templateService;

    @Mock
    private ITemplateEngine templateEngine;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenBuild_thenReturnString() {
        String template = CommonTestUtils.TEMPLATE;
        Map<String, Object> variables = new HashMap<>();
        String expected = RandomStringUtils.random(10);
        Mockito.when(templateEngine.process(Mockito.eq(template), Mockito.any(Context.class))).thenReturn(expected);

        String actual = templateService.build(template, variables);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenBuild_thenReturnString2() {
        String template = CommonTestUtils.TEMPLATE;
        String expected = RandomStringUtils.random(10);
        Mockito.when(templateEngine.process(Mockito.eq(template), Mockito.any(Context.class))).thenReturn(expected);

        String variableName = RandomStringUtils.random(10);
        String variableValue = RandomStringUtils.random(10);
        String actual = templateService.build(template, variableName, variableValue);

        Assertions.assertEquals(expected, actual);
    }
}