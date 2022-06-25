package com.example.socialnetworkapp.service;


import com.example.socialnetworkapp.enums.MasterErrorCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.MasterErrorMessage;

public interface MasterErrorMessageService {

    MasterErrorMessage findByErrorCode(MasterErrorCode errorCode) throws ResourceNotFoundException;

}
