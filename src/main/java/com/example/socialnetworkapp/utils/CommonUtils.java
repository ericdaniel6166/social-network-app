package com.example.socialnetworkapp.utils;

import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Slf4j
public final class CommonUtils {

    public static final String REGEX_MASKING_EMAIL = "(?<=.)[^@](?=[^@]*?[^@]@)|(?:(?<=@.)|(?!^)\\G(?=[^@]*$)).(?=.*[^@]\\.)";

    public static final String CHARACTER_ASTERISK = "*";

    public static final String PAGE_REQUEST_DIRECTION_ASC = "ASC";

    public static final String PAGE_REQUEST_DIRECTION_DESC = "DESC";

    public static final String PAGE_REQUEST_SIZE_DEFAULT = "10";

    public static final String PAGE_REQUEST_PAGE_NUMBER_DEFAULT = "0";

    public static final String PAGE_REQUEST_PROPERTIES_LAST_MODIFIED_DATE = "lastModifiedDate";

    public static String maskEmail(String email) {
        return email.replaceAll(REGEX_MASKING_EMAIL, CHARACTER_ASTERISK);
    }

    public static String formatString(String string, Object... objects) throws SocialNetworkAppException {
        String formattedString;
        try {
            formattedString = String.format(string, objects);
        } catch (Exception e) {
            log.error("Error format string, string: {}, arguments: {}", string, Arrays.asList(objects), e);
            throw new SocialNetworkAppException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
        }
        return formattedString;
    }

}
