package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.configuration.JwtConfiguration;
import com.example.socialnetworkapp.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder jwtEncoder;

    @Autowired
    private JwtConfiguration jwtConfiguration;

    @Override
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return buildToken(user);
    }

    private String buildToken(User user) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(jwtConfiguration.getJwtExpirationInMillis()))
                .subject(user.getUsername())
                .claim("scope", getAuthorityList(user))
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private List<String> getAuthorityList(User user) {
        List<String> authorityList = new ArrayList<>();
        user.getAuthorities().forEach(authority -> authorityList.add(authority.getAuthority()));
        return authorityList;
    }
}
