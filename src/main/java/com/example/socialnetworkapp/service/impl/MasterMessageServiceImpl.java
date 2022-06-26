package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.repository.MasterMessageRepository;
import com.example.socialnetworkapp.service.MasterMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterMessageServiceImpl implements MasterMessageService {

    @Autowired
    private MasterMessageRepository masterMessageRepository;

    @Override
    public MasterMessage findByMessageCode(MasterMessageCode messageCode) throws ResourceNotFoundException {
        return masterMessageRepository.findByMessageCode(messageCode).orElseThrow(
                () -> new ResourceNotFoundException("Message code " + messageCode.name()));
    }
}
