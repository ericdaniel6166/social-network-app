package com.example.socialnetworkapp.exception;

import com.example.socialnetworkapp.dto.ErrorDetail;
import com.example.socialnetworkapp.enums.ErrorCode;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
public class ValidationException extends SocialNetworkAppException {

    public ValidationException(HttpStatus httpStatus, String message, List<ErrorDetail> errorDetails) {
        super(httpStatus, ErrorCode.VALIDATION_ERROR.name(), message, errorDetails);
    }
}
