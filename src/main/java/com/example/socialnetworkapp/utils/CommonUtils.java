package com.example.socialnetworkapp.utils;

import com.example.socialnetworkapp.enums.ErrorCode;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.IllegalFormatException;

@Slf4j
public final class CommonUtils {

    public static final String REGEX_MASKING_EMAIL = "(?<=.)[^@](?=[^@]*?[^@]@)|(?:(?<=@.)|(?!^)\\G(?=[^@]*$)).(?=.*[^@]\\.)";

    public static final String MASKING_CHARACTER_ASTERISK = "*";

    public static String maskEmail(String email) {
        return email.replaceAll(REGEX_MASKING_EMAIL, MASKING_CHARACTER_ASTERISK);
    }

    public static String formatString(String string, Object... objects) throws SocialNetworkAppException {
        String formattedString;
        try {
            formattedString = String.format(string, objects);
        } catch (IllegalFormatException e) {
            log.error("Error format string, string: {}, arguments: {}", string, Arrays.asList(objects), e);
            throw new SocialNetworkAppException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.FORMAT_ERROR.name(), e.getMessage(), null);
        }
        return formattedString;
    }

}
