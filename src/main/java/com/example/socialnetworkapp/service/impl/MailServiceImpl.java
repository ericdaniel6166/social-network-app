package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.dto.EmailDTO;
import com.example.socialnetworkapp.dto.SendMailErrorDetail;
import com.example.socialnetworkapp.enums.ErrorCode;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.service.MailBuilderService;
import com.example.socialnetworkapp.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private static final String VERIFICATION_EMAIL = "verification@socialnetworkapp.com";

    @Autowired
    private MailBuilderService mailBuilderService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ModelMapper modelMapper;

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
            SendMailErrorDetail sendMailErrorDetailDTO = modelMapper.map(emailDTO, SendMailErrorDetail.class);
            log.error("Send mail fail, error message: {}", e.getMessage(), e);
            //TODO get message from master_error_message from database
            throw new SocialNetworkAppException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.SEND_MAIL_ERROR.name()
                    , "Error occurred sending email to: " + emailDTO.getRecipient()
                    , Collections.singletonList(sendMailErrorDetailDTO));
        }
    }

}
