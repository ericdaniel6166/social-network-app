package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.auth.service.JwtService;
import com.example.socialnetworkapp.configuration.security.JwtConfiguration;
import com.example.socialnetworkapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final JwtConfiguration jwtConfiguration;

    @Override
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return buildToken(user.getUsername(), getAuthorityList(user));
    }

    @Override
    public String buildToken(String username, List<String> scope) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(Constants.SELF)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(jwtConfiguration.getJwtExpirationInMillis()))
                .subject(username)
                .claim(Constants.SCOPE, scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private List<String> getAuthorityList(User user) {
        List<String> authorityList = new ArrayList<>();
        user.getAuthorities().forEach(authority -> authorityList.add(authority.getAuthority()));
        return authorityList;
    }
}
