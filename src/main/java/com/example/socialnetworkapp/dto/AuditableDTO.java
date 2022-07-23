package com.example.socialnetworkapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class AuditableDTO<U> implements Serializable {

    protected U createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected LocalDateTime createdDate;

    protected U lastModifiedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected LocalDateTime lastModifiedDate;
}
