package com.example.socialnetworkapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SendMailErrorDetail extends ErrorDetail {

    private String subject;

    private String recipient;

    @JsonIgnore
    private String body;

}
