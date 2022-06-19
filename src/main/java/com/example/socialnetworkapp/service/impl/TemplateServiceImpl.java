package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public String build(String template, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(template, context);
    }

    @Override
    public String build(String template, String variableName, Object variableValue) {
        Context context = new Context();
        context.setVariable(variableName, variableValue);
        return templateEngine.process(template, context);
    }
}
