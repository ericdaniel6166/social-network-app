package com.example.socialnetworkapp.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@RequiredArgsConstructor
public class MailConfiguration {

    //TODO: move to db master_general_parameter
    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_DEBUG = "mail.debug";

    private final MailProperties mailProperties;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());

        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put(MAIL_TRANSPORT_PROTOCOL, mailProperties.getProperties().get(MAIL_TRANSPORT_PROTOCOL));
        properties.put(MAIL_SMTP_AUTH, mailProperties.getProperties().get(MAIL_SMTP_AUTH));
        properties.put(MAIL_SMTP_STARTTLS_ENABLE, mailProperties.getProperties().get(MAIL_SMTP_STARTTLS_ENABLE));
        properties.put(MAIL_DEBUG, mailProperties.getProperties().get(MAIL_DEBUG));

        return mailSender;
    }
}
