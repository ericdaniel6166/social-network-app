package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.dto.EmailDTO;
import com.example.socialnetworkapp.enums.MasterErrorCode;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.model.MasterErrorMessage;
import com.example.socialnetworkapp.service.MailBuilderService;
import com.example.socialnetworkapp.service.MasterErrorMessageService;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import com.mysql.cj.util.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

@Slf4j
class MailServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private MailServiceImpl mailService;

    @Mock
    private MailBuilderService mailBuilderService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MasterErrorMessageService masterErrorMessageService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenSendMail_thenSuccess() throws SocialNetworkAppException {
        EmailDTO emailDTO = CommonTestUtils.buildEmailDTO(Constants.VERIFICATION_EMAIL_SUBJECT, AuthTestUtils.EMAIL);
        mailService.sendMail(emailDTO);
    }

    @Test
    void whenSendMail_thenThrowMailException() throws SocialNetworkAppException {
        EmailDTO emailDTO = CommonTestUtils.buildEmailDTO(Constants.VERIFICATION_EMAIL_SUBJECT, AuthTestUtils.EMAIL);

        MasterErrorMessage masterErrorMessage = CommonTestUtils.buildMasterErrorMessage(MasterErrorCode.SEND_MAIL_ERROR);
        String errorMessage = StringEscapeUtils.unescapeJava(masterErrorMessage.getErrorMessage());
        Mockito.when(masterErrorMessageService.findByErrorCode(MasterErrorCode.SEND_MAIL_ERROR)).thenReturn(masterErrorMessage);
        SocialNetworkAppException expected = new SocialNetworkAppException(HttpStatus.INTERNAL_SERVER_ERROR, MasterErrorCode.SEND_MAIL_ERROR.name(),
                CommonUtils.formatString(errorMessage, CommonUtils.maskEmail(emailDTO.getRecipient())),
                null);

        try {
            Mockito.doThrow(new MailSendException("Mail send exception error message")).when(javaMailSender).send(Mockito.any(MimeMessagePreparator.class));
            mailService.sendMail(emailDTO);
        } catch (SocialNetworkAppException e){
            Assertions.assertEquals(expected, e);
        }
    }


}