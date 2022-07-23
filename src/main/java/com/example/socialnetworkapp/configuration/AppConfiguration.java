package com.example.socialnetworkapp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("com.example.socialnetworkapp")
@Data
public class AppConfiguration {

    private String timeZoneId;

}
