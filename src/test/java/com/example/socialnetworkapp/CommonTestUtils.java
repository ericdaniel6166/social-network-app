package com.example.socialnetworkapp;

import com.example.socialnetworkapp.dto.MasterErrorMessageDTO;
import com.example.socialnetworkapp.dto.MasterMessageDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.model.MasterErrorMessage;

public class CommonTestUtils {

    public static final String ZONED_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss Z";

    public static final String TIME_ZONE_ID = "Asia/Ho_Chi_Minh";

    public static final String ERROR_MESSAGE = "errorMessage";

    public static final String MESSAGE = "message";

    public static final String TITLE = "title";

    public static SimpleResponseDTO buildSimpleResponseDTO() {
        SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
        simpleResponseDTO.setTitle(TITLE);
        simpleResponseDTO.setMessage(MESSAGE);
        return simpleResponseDTO;
    }

    public static MasterErrorMessage buildMasterErrorMessage() {
        MasterErrorMessage masterErrorMessage = new MasterErrorMessage();
        masterErrorMessage.setErrorMessage(ERROR_MESSAGE);
        return masterErrorMessage;
    }

    public static MasterErrorMessageDTO buildMasterErrorMessageDTO() {
        MasterErrorMessageDTO masterErrorMessageDTO = new MasterErrorMessageDTO();
        masterErrorMessageDTO.setErrorMessage(ERROR_MESSAGE);
        return masterErrorMessageDTO;
    }

    public static MasterMessageDTO buildMasterMessageDTO() {
        MasterMessageDTO masterMessageDTO = new MasterMessageDTO();
        masterMessageDTO.setMessage(MESSAGE);
        return masterMessageDTO;
    }

}
