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
public class SimpleResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private String message;

}
