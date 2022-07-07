package com.example.socialnetworkapp.service;


import com.example.socialnetworkapp.dto.MasterErrorMessageDTO;
import com.example.socialnetworkapp.enums.MasterErrorCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.MasterErrorMessage;

import java.util.List;

public interface MasterErrorMessageService {

    MasterErrorMessage findByErrorCode(MasterErrorCode errorCode) throws ResourceNotFoundException;

    List<MasterErrorMessageDTO> findAll();
}
