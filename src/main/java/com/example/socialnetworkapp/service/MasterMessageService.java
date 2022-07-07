package com.example.socialnetworkapp.service;


import com.example.socialnetworkapp.dto.MasterMessageDTO;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.MasterMessage;

import java.util.List;

public interface MasterMessageService {

    MasterMessage findByMessageCode(MasterMessageCode messageCode) throws ResourceNotFoundException;

    List<MasterMessageDTO> findAll();
}
