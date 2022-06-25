package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.dto.EmailDTO;
import com.example.socialnetworkapp.enums.MasterErrorCode;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.model.MasterErrorMessage;
import com.example.socialnetworkapp.service.MailBuilderService;
import com.example.socialnetworkapp.service.MailService;
import com.example.socialnetworkapp.service.MasterErrorMessageService;
import com.example.socialnetworkapp.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private static final String VERIFICATION_EMAIL = "verification@socialnetworkapp.com";

    @Autowired
    private MailBuilderService mailBuilderService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MasterErrorMessageService masterErrorMessageService;

    @Override
    public void sendMail(EmailDTO emailDTO) throws SocialNetworkAppException {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(VERIFICATION_EMAIL);
            mimeMessageHelper.setTo(emailDTO.getRecipient());
            mimeMessageHelper.setSubject(emailDTO.getSubject());
            mimeMessageHelper.setText(mailBuilderService.buildMail(emailDTO.getBody()));
        };
        try {
            javaMailSender.send(mimeMessagePreparator);
            log.info("Send mail success");
        } catch (MailException e) {
            log.error("Send mail fail, error message: {}, subject: {}, from: {}, to: {}", e.getMessage(), emailDTO.getSubject(), VERIFICATION_EMAIL, emailDTO.getRecipient(), e);
            MasterErrorMessage masterErrorMessage = masterErrorMessageService.findByErrorCode(MasterErrorCode.SEND_MAIL_ERROR);
            String errorMessage = StringEscapeUtils.unescapeJava(masterErrorMessage.getErrorMessage());
            throw new SocialNetworkAppException(HttpStatus.INTERNAL_SERVER_ERROR, MasterErrorCode.SEND_MAIL_ERROR.name()
                    , CommonUtils.formatString(errorMessage, CommonUtils.maskEmail(emailDTO.getRecipient()))
                    , null);
        }
    }

}
