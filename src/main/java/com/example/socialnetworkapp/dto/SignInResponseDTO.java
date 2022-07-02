package com.example.socialnetworkapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SignInResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accessToken;

    private String refreshToken;

    private String expiresAt;

}
