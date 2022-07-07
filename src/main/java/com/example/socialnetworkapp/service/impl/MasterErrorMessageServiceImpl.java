package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.dto.MasterErrorMessageDTO;
import com.example.socialnetworkapp.dto.MasterMessageDTO;
import com.example.socialnetworkapp.enums.MasterErrorCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.MasterErrorMessage;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.repository.MasterErrorMessageRepository;
import com.example.socialnetworkapp.service.MasterErrorMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MasterErrorMessageServiceImpl implements MasterErrorMessageService {

    private final MasterErrorMessageRepository masterErrorMessageRepository;

    private final ModelMapper modelMapper;

    @Override
    public MasterErrorMessage findByErrorCode(MasterErrorCode errorCode) throws ResourceNotFoundException {
        log.debug("Find master error message by error code, error code: {}", errorCode);
        return masterErrorMessageRepository.findByErrorCode(errorCode).orElseThrow(
                () -> new ResourceNotFoundException("Error code " + errorCode.name()));
    }

    @Override
    public List<MasterErrorMessageDTO> findAll() {
        List<MasterErrorMessage> masterErrorMessageList = masterErrorMessageRepository.findAll();
        return masterErrorMessageList.stream()
                .map(masterErrorMessage -> {
                    masterErrorMessage.setErrorMessage(StringEscapeUtils.unescapeJava(masterErrorMessage.getErrorMessage()));
                    return modelMapper.map(masterErrorMessage, MasterErrorMessageDTO.class);
                })
                .collect(Collectors.toList());
    }
}
