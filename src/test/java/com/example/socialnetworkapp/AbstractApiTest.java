package com.example.socialnetworkapp;

import com.example.socialnetworkapp.configuration.OperationIdConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = OperationIdConfiguration.class)
@ComponentScan(value = "com.example.socialnetworkapp.auth.controller")
public abstract class AbstractApiTest {
    protected static final String UTF_8 = "UTF-8";
}
