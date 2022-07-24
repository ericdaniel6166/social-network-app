package com.example.socialnetworkapp.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RefreshTokenRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;

    @NotBlank(message = "Username is required")
    private String username;
}
