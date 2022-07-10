package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.service.MailBuilderService;
import com.example.socialnetworkapp.service.TemplateService;
import com.example.socialnetworkapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailBuilderServiceImpl implements MailBuilderService {

    private final TemplateService templateService;

    @Override
    public String buildMail(String message) {
        return templateService.build(Constants.MAIL_TEMPLATE_NAME, Constants.MAIL_VARIABLE_NAME, message);
    }
}
