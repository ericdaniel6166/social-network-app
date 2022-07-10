package com.example.socialnetworkapp;

import com.example.socialnetworkapp.dto.MasterErrorMessageDTO;
import com.example.socialnetworkapp.dto.MasterMessageDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.model.MasterErrorMessage;
import com.example.socialnetworkapp.utils.CommonUtils;
import com.example.socialnetworkapp.utils.Constants;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;

public class CommonTestUtils {

    public static final String ZONED_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss Z";

    public static final String TIME_ZONE_ID = "Asia/Ho_Chi_Minh";

    public static final String ERROR_MESSAGE = "errorMessage";

    public static final String MESSAGE = "message";

    public static final String TITLE = "title";

    public static final String SEARCH = "name==*red*";

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

}
