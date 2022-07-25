package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.dto.MasterMessageDTO;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.repository.MasterMessageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class MasterMessageServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private MasterMessageServiceImpl masterMessageService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MasterMessageRepository masterMessageRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenFindAll_thenReturnMasterMessageDTOList() {
        MasterMessageCode messageCode = MasterMessageCode.CREATE_SUCCESS;
        MasterMessage masterMessage = CommonTestUtils.buildMasterMessage(messageCode);
        MasterMessageDTO masterMessageDTO = CommonTestUtils.buildMasterMessageDTO(messageCode);
        List<MasterMessageDTO> expected = Collections.singletonList(masterMessageDTO);
        Mockito.when(modelMapper.map(masterMessage, MasterMessageDTO.class)).thenReturn(masterMessageDTO);
        Mockito.when(masterMessageRepository.findAll()).thenReturn(Collections.singletonList(masterMessage));

        List<MasterMessageDTO> actual = masterMessageService.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenFindByErrorCode_givenNotEmptyMasterErrorMessage_thenReturnMasterErrorMessage() throws ResourceNotFoundException {
        MasterMessageCode messageCode = MasterMessageCode.CREATE_SUCCESS;
        MasterMessage expected = CommonTestUtils.buildMasterMessage(messageCode);
        Mockito.when(masterMessageRepository.findByMessageCode(messageCode)).thenReturn(Optional.of(expected));

        MasterMessage actual = masterMessageService.findByMessageCode(messageCode);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenFindByErrorCode_givenEmptyMasterErrorMessage_thenThrowResourceNotFoundException() {
        MasterMessageCode messageCode = MasterMessageCode.CREATE_SUCCESS;
        Mockito.when(masterMessageRepository.findByMessageCode(messageCode)).thenReturn(Optional.empty());
        ResourceNotFoundException expected = new ResourceNotFoundException("Message code " + messageCode.name());

        try {
            masterMessageService.findByMessageCode(messageCode);
        } catch (ResourceNotFoundException e) {
            Assertions.assertEquals(expected, e);
        }

    }


}