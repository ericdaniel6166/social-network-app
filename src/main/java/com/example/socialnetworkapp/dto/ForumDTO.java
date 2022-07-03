package com.example.socialnetworkapp.dto;

import com.example.socialnetworkapp.utils.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ForumDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date lastModifiedDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date createdDate;

}
