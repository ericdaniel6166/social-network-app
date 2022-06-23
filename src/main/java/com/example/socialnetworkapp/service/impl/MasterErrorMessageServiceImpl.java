package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.MasterErrorMessage;
import com.example.socialnetworkapp.repository.MasterErrorMessageRepository;
import com.example.socialnetworkapp.service.MasterErrorMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterErrorMessageServiceImpl implements MasterErrorMessageService {

    @Autowired
    private MasterErrorMessageRepository masterErrorMessageRepository;

    @Override
    public MasterErrorMessage findByErrorCode(String errorCode) throws ResourceNotFoundException {
        return masterErrorMessageRepository.findByErrorCode(errorCode).orElseThrow(() -> new ResourceNotFoundException(errorCode));
    }
}
