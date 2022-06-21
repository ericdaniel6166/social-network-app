package com.example.socialnetworkapp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String subject;

    private String recipient;

    private String body;
}
