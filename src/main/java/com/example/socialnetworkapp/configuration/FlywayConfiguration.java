package com.example.socialnetworkapp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.flyway")
@Data
public class FlywayConfiguration {

    private String locations;
    private String baselineVersion;
    private boolean baselineOnMigrate;
    private boolean enabled;
    private boolean isActive;
    private String table;
    private String driverVendor;

}
