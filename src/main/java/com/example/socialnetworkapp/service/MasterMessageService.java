package com.example.socialnetworkapp.service;


import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.MasterMessage;

public interface MasterMessageService {

    MasterMessage findByMessageCode(MasterMessageCode messageCode) throws ResourceNotFoundException;

}
