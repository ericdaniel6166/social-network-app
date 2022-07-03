package com.example.socialnetworkapp.utils;

import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Slf4j
public final class CommonUtils {

    public static final String REGEX_MASKING_EMAIL = "(?<=.)[^@](?=[^@]*?[^@]@)|(?:(?<=@.)|(?!^)\\G(?=[^@]*$)).(?=.*[^@]\\.)";

    public static final String CHARACTER_ASTERISK = "*";

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

    public static void checkPageNotEmpty(PageImpl<?> page) throws ResourceNotFoundException {
        if (page.getContent().isEmpty()) {
            throw new ResourceNotFoundException("Page");
        }
    }

    public static Pageable buildPageable(Integer page, Integer size, String direction, String[] properties) {
        log.info("Build pageable, page: {}, size: {}, direction: {}, properties: {}", page, size, direction, properties);
        Sort.Direction pageRequestDirection = Constants.PAGE_REQUEST_DIRECTION_ASC.equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(page, size, pageRequestDirection, properties);
    }

}
