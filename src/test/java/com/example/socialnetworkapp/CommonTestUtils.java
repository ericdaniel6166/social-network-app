package com.example.socialnetworkapp;

import com.example.socialnetworkapp.dto.EmailDTO;
import com.example.socialnetworkapp.dto.MasterErrorMessageDTO;
import com.example.socialnetworkapp.dto.MasterMessageDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.enums.MasterErrorCode;
import com.example.socialnetworkapp.enums.MasterMessageCode;
import com.example.socialnetworkapp.model.MasterErrorMessage;
import com.example.socialnetworkapp.model.MasterMessage;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonTestUtils {

    public static final String ZONED_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss Z";

    public static final String TIME_ZONE_ID = "Asia/Ho_Chi_Minh";

    public static final String ERROR_MESSAGE = "errorMessage";

    public static final String MESSAGE = "message";

    public static final String TITLE = "title";

    public static final String SEARCH = "name==*red*";

    public static final String TEMPLATE = "template";

    public static SimpleResponseDTO buildSimpleResponseDTO() {
        SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
        simpleResponseDTO.setTitle(TITLE);
        simpleResponseDTO.setMessage(MESSAGE);
        return simpleResponseDTO;
    }

    public static MasterErrorMessage buildMasterErrorMessage(MasterErrorCode masterErrorCode) {
        MasterErrorMessage masterErrorMessage = new MasterErrorMessage();
        masterErrorMessage.setErrorMessage(ERROR_MESSAGE);
        masterErrorMessage.setErrorCode(masterErrorCode);
        return masterErrorMessage;
    }

    public static MasterErrorMessageDTO buildMasterErrorMessageDTO(MasterErrorCode masterErrorCode) {
        MasterErrorMessageDTO masterErrorMessageDTO = new MasterErrorMessageDTO();
        MasterErrorMessage masterErrorMessage = buildMasterErrorMessage(masterErrorCode);
        masterErrorMessageDTO.setErrorMessage(masterErrorMessage.getErrorMessage());
        masterErrorMessageDTO.setErrorCode(masterErrorMessage.getErrorCode());
        return masterErrorMessageDTO;
    }

    public static MasterMessageDTO buildMasterMessageDTO(MasterMessageCode masterMessageCode) {
        MasterMessageDTO masterMessageDTO = new MasterMessageDTO();
        MasterMessage masterMessage = buildMasterMessage(masterMessageCode);
        masterMessageDTO.setMessage(masterMessage.getMessage());
        masterMessageDTO.setTitle(masterMessage.getTitle());
        masterMessageDTO.setMessageCode(masterMessage.getMessageCode());
        return masterMessageDTO;
    }

    public static MasterMessage buildMasterMessage(MasterMessageCode masterMessageCode) {
        MasterMessage masterMessage = new MasterMessage();
        masterMessage.setMessage(MESSAGE);
        masterMessage.setTitle(TITLE);
        masterMessage.setMessageCode(masterMessageCode);
        return masterMessage;
    }


    public static Page<?> buildPage(Object... objects) {
        Pageable pageable = buildPageable();
        long totalElement = 10;
        return new PageImpl<>(Arrays.asList(objects), pageable, totalElement);
    }

    public static Pageable buildPageable() {
        Integer page = Integer.parseInt(Constants.PAGE_REQUEST_PAGE_NUMBER_DEFAULT);
        Integer size = Integer.parseInt(Constants.PAGE_REQUEST_SIZE_DEFAULT);
        Sort.Direction direction = Sort.Direction.DESC;
        String[] properties = ArrayUtils.toArray(Constants.PAGE_REQUEST_PROPERTIES_LAST_MODIFIED_DATE, "property");


        return CommonUtils.buildPageable(page, size, direction, properties);
    }

    public static EmailDTO buildEmailDTO(String subject, String recipient) {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setSubject(subject);
        emailDTO.setRecipient(recipient);
        emailDTO.setBody(RandomStringUtils.random(10));
        return emailDTO;
    }

    public static List<MasterErrorMessage> buildMasterErrorMessageList(MasterErrorCode... masterErrorCodes) {
        List<MasterErrorMessage> masterErrorMessageList = new ArrayList<>();
        for (MasterErrorCode masterErrorCode: masterErrorCodes){
            masterErrorMessageList.add(buildMasterErrorMessage(masterErrorCode));
        }
        return masterErrorMessageList;
    }
}
