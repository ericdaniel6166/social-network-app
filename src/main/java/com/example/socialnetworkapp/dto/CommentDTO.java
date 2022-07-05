package com.example.socialnetworkapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Content is required")
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date lastModifiedDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date createdDate;

    private Long postId;

    private String username;
}
