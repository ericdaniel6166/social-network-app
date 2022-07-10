package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.forum.service.impl.ForumServiceImpl;
import com.example.socialnetworkapp.service.MasterMessageService;
import com.example.socialnetworkapp.service.TemplateService;
import com.example.socialnetworkapp.utils.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MailBuilderServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private MailBuilderServiceImpl mailBuilderService;

    @Mock
    private TemplateService templateService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenBuildMail_thenReturnMailText() {
        String message = RandomStringUtils.random(10);
        String expected = RandomStringUtils.random(10);
        Mockito.when(templateService.build(Constants.MAIL_TEMPLATE_NAME, Constants.MAIL_VARIABLE_NAME, message))
                .thenReturn(expected);

        String actual = mailBuilderService.buildMail(message);

        Assertions.assertEquals(expected, actual);
    }
}