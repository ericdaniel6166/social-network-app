package com.example.socialnetworkapp.dto;

import com.example.socialnetworkapp.enums.MasterMessageCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MasterMessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Enumerated(EnumType.STRING)
    private MasterMessageCode messageCode;

    private String message;

    private String title;

}
