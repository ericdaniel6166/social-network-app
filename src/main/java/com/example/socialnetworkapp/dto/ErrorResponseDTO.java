package com.example.socialnetworkapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ErrorResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date timestamp;

    private int status;

    @JsonIgnore
    private HttpStatus httpStatus;

    private String operationId;

    private String error;

    private String message;

    private String path;

    private List<ErrorDetail> errorDetails;

    public ErrorResponseDTO() {
        this.timestamp = Date.from(Instant.now());
    }

    public ErrorResponseDTO(HttpStatus httpStatus, String error, String message, HttpServletRequest httpServletRequest, List<ErrorDetail> errorDetails) {
        this();
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
        this.error = error;
        this.message = message;
        this.path = httpServletRequest.getRequestURI();
        this.errorDetails = errorDetails;
    }


}
