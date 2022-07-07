package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.dto.MasterMessageDTO;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.repository.MasterMessageRepository;
import com.example.socialnetworkapp.service.MasterMessageService;
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
public class MasterMessageServiceImpl implements MasterMessageService {

    private final MasterMessageRepository masterMessageRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<MasterMessageDTO> findAll() {
        List<MasterMessage> masterMessageList = masterMessageRepository.findAll();
        return masterMessageList.stream()
                .map(masterMessage -> {
                    masterMessage.setTitle(StringEscapeUtils.unescapeJava(masterMessage.getTitle()));
                    masterMessage.setMessage(StringEscapeUtils.unescapeJava(masterMessage.getMessage()));
                    return modelMapper.map(masterMessage, MasterMessageDTO.class);
                })
                .collect(Collectors.toList());
    }


    @Override
    public MasterMessage findByMessageCode(MasterMessageCode messageCode) throws ResourceNotFoundException {
        log.debug("Find master message by message code, message code: {}", messageCode);
        return masterMessageRepository.findByMessageCode(messageCode).orElseThrow(
                () -> new ResourceNotFoundException("Message code " + messageCode.name()));
    }
}
