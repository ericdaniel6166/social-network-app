package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.CommonTestUtils;
import com.example.socialnetworkapp.dto.MasterErrorMessageDTO;
import com.example.socialnetworkapp.enums.MasterErrorCode;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.MasterErrorMessage;
import com.example.socialnetworkapp.repository.MasterErrorMessageRepository;
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

class MasterErrorMessageServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private MasterErrorMessageServiceImpl masterErrorMessageService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MasterErrorMessageRepository masterErrorMessageRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenFindByErrorCode_givenNotEmptyMasterErrorMessage_thenReturnMasterErrorMessage() throws ResourceNotFoundException {
        MasterErrorCode errorCode = MasterErrorCode.USERNAME_EXISTED_ERROR;
        MasterErrorMessage expected = CommonTestUtils.buildMasterErrorMessage(errorCode);
        Mockito.when(masterErrorMessageRepository.findByErrorCode(errorCode)).thenReturn(Optional.of(expected));

        MasterErrorMessage actual = masterErrorMessageService.findByErrorCode(errorCode);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenFindByErrorCode_givenEmptyMasterErrorMessage_thenThrowResourceNotFoundException() {
        MasterErrorCode errorCode = MasterErrorCode.USERNAME_EXISTED_ERROR;
        Mockito.when(masterErrorMessageRepository.findByErrorCode(errorCode)).thenReturn(Optional.empty());
        ResourceNotFoundException expected = new ResourceNotFoundException("Error code " + errorCode.name());
        try {
            masterErrorMessageService.findByErrorCode(errorCode);
        } catch (ResourceNotFoundException e) {
            Assertions.assertEquals(expected, e);
        }

    }


    @Test
    void whenFindAll_thenReturnMasterErrorMessageDTOList() {
        MasterErrorCode errorCode = MasterErrorCode.EMAIL_EXISTED_ERROR;
        MasterErrorMessage masterErrorMessage = CommonTestUtils.buildMasterErrorMessage(errorCode);
        MasterErrorMessageDTO masterErrorMessageDTO = CommonTestUtils.buildMasterErrorMessageDTO(errorCode);
        List<MasterErrorMessageDTO> expected = Collections.singletonList(masterErrorMessageDTO);
        Mockito.when(modelMapper.map(masterErrorMessage, MasterErrorMessageDTO.class)).thenReturn(masterErrorMessageDTO);
        Mockito.when(masterErrorMessageRepository.findAll()).thenReturn(Collections.singletonList(masterErrorMessage));

        List<MasterErrorMessageDTO> actual = masterErrorMessageService.findAll();

        Assertions.assertEquals(expected, actual);
    }
}