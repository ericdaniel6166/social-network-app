package com.example.socialnetworkapp.exception;

import com.example.socialnetworkapp.enums.ErrorCode;

public class ResourceNotFoundException extends SocialNetworkAppException {

    public ResourceNotFoundException(String resource) {
        super(ErrorCode.RESOURCE_NOT_FOUND_ERROR.name(), String.format("%s not found", resource));
    }

}
