package com.example.socialnetworkapp;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;

@ExtendWith(MockitoExtension.class)
//@ComponentScan(value = "com.example.socialnetworkapp.auth.service.impl")
@ComponentScan(value = {"com.example.socialnetworkapp.auth.service.impl",
        "com.example.socialnetworkapp.service.impl",
        "com.example.socialnetworkapp.forum.service.impl"
})
public abstract class AbstractServiceTest {
}
