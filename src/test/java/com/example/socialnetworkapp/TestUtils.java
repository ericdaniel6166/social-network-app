package com.example.socialnetworkapp;

import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.model.MasterErrorMessage;

public class TestUtils {

    public static final String ZONED_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss Z";

    public static final String TIME_ZONE_ID = "Asia/Ho_Chi_Minh";

    public static SimpleResponseDTO buildSimpleResponseDTO() {
        SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
        simpleResponseDTO.setTitle("title");
        simpleResponseDTO.setMessage("message");
        return simpleResponseDTO;
    }

    public static MasterErrorMessage buildMasterErrorMessage() {
        MasterErrorMessage masterErrorMessage = new MasterErrorMessage();
        masterErrorMessage.setErrorMessage("errorMessage");
        return masterErrorMessage;
    }

}
