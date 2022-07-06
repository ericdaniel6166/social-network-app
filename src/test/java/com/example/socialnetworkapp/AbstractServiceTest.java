package com.example.socialnetworkapp;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;

@ExtendWith(MockitoExtension.class)
@ComponentScan(value = "com.example.socialnetworkapp.auth.service.impl")
public abstract class AbstractServiceTest {
}
