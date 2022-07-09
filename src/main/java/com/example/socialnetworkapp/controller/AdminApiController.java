package com.example.socialnetworkapp.controller;

import com.example.socialnetworkapp.dto.MasterErrorMessageDTO;
import com.example.socialnetworkapp.dto.MasterMessageDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import com.example.socialnetworkapp.service.MasterErrorMessageService;
import com.example.socialnetworkapp.service.MasterMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminApiController implements CommonApi {
    //TODO: implement ROLE_ADMIN

    private final MasterMessageService masterMessageService;

    private final MasterErrorMessageService masterErrorMessageService;

    @Override
    @GetMapping("/masterErrorMessage")
    public ResponseEntity<?> findAllMasterErrorMessage() throws SocialNetworkAppException {
        List<MasterErrorMessageDTO> masterErrorMessageDTOList = masterErrorMessageService.findAll();
        return new ResponseEntity<>(masterErrorMessageDTOList, HttpStatus.OK);
    }

    @Override
    @GetMapping("/masterMessage")
    public ResponseEntity<?> findAllMasterMessage() throws SocialNetworkAppException {
        List<MasterMessageDTO> masterMessageList = masterMessageService.findAll();
        return new ResponseEntity<>(masterMessageList, HttpStatus.OK);
    }

}
