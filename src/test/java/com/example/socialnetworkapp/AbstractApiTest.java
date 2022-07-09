package com.example.socialnetworkapp;

import com.example.socialnetworkapp.configuration.OperationIdConfiguration;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = OperationIdConfiguration.class)
public abstract class AbstractApiTest {
    protected static final String UTF_8 = "UTF-8";
}
