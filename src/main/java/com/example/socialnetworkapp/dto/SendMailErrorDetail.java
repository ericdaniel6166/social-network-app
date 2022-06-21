package com.example.socialnetworkapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SendMailErrorDetail extends ErrorDetail {

    private String subject;

    private String recipient;

    @JsonIgnore
    private String body;

}
