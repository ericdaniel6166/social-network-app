package com.example.socialnetworkapp.utils;

public final class CommonUtils {

    public static final String REGEX_MASKING_EMAIL = "(?<=.)[^@](?=[^@]*?[^@]@)|(?:(?<=@.)|(?!^)\\G(?=[^@]*$)).(?=.*[^@]\\.)";

    public static final String MASKING_CHARACTER_ASTERISK = "*";

    public static String maskEmail(String email) {

        return email.replaceAll(REGEX_MASKING_EMAIL, MASKING_CHARACTER_ASTERISK);
    }

}
