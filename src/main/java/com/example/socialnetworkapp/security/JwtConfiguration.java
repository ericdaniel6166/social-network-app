package com.example.socialnetworkapp.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class JwtConfiguration {

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

}
