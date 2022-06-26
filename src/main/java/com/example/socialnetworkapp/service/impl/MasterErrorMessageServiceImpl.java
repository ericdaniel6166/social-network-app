package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.enums.MasterErrorCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.MasterErrorMessage;
import com.example.socialnetworkapp.repository.MasterErrorMessageRepository;
import com.example.socialnetworkapp.service.MasterErrorMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MasterErrorMessageServiceImpl implements MasterErrorMessageService {

    @Autowired
    private MasterErrorMessageRepository masterErrorMessageRepository;

    @Override
    public MasterErrorMessage findByErrorCode(MasterErrorCode errorCode) throws ResourceNotFoundException {
        log.info("Start find master error message by error code, error code: {}", errorCode);
        return masterErrorMessageRepository.findByErrorCode(errorCode).orElseThrow(
                () -> new ResourceNotFoundException("Error code " + errorCode.name()));
    }
}
