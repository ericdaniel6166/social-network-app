package com.example.socialnetworkapp.dto;

import com.example.socialnetworkapp.enums.MasterErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MasterErrorMessageDTO implements Serializable {

    private Long id;

    @Enumerated(EnumType.STRING)
    private MasterErrorCode errorCode;

    private String errorMessage;
}
