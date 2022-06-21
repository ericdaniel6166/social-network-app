package com.example.socialnetworkapp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String message;

}
