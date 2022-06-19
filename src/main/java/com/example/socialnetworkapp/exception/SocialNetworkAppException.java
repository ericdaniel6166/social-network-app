package com.example.socialnetworkapp.exception;

import com.example.socialnetworkapp.dto.ErrorDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class SocialNetworkAppException extends Exception {

    private String error;

    private HttpStatus httpStatus;

    private int status;

    private List<ErrorDetail> errorDetails;

    public SocialNetworkAppException(String error, String message) {
        super(message);
        this.error = error;
    }

    public SocialNetworkAppException(String error, String message, List<ErrorDetail> errorDetails) {
        super(message);
        this.error = error;
        this.errorDetails = errorDetails;
    }

    public SocialNetworkAppException(HttpStatus httpStatus, String error, String message, List<ErrorDetail> errorDetails) {
        super(message);
        this.error = error;
        this.errorDetails = errorDetails;
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
    }


}
