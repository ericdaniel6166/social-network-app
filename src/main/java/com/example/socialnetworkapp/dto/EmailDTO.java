package com.example.socialnetworkapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String subject;

    private String recipient;

    private String body;
}
