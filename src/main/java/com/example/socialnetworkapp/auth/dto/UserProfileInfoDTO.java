package com.example.socialnetworkapp.auth.dto;

import com.example.socialnetworkapp.utils.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserProfileInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String fullName;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate birthday;

    private String address;
}
