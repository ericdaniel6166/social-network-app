package com.example.socialnetworkapp.exception;

import com.example.socialnetworkapp.enums.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class ValidationException extends SocialNetworkAppException {

    private String object;

    private String field;

    private Serializable rejectedValue;

    public ValidationException(String object, String field, Serializable rejectedValue, String message) {
        super(ErrorCode.VALIDATION_ERROR.name(), message);
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
    }

}
