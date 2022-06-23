package com.example.socialnetworkapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ErrorResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY HH:mm:ss")
    private LocalDateTime timestamp;

    private int status;

    @JsonIgnore
    private HttpStatus httpStatus;

    private String operationId;

    private String error;

    private String message;

    private String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorDetail> errorDetails;

    public ErrorResponseDTO() {
        this.timestamp = LocalDateTime.now();
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
