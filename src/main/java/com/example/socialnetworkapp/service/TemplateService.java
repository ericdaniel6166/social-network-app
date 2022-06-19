package com.example.socialnetworkapp.service;

import java.util.Map;

public interface TemplateService {

    String build(String template, Map<String, Object> map);

    String build(String template, String variableName, Object variableValue);

}
