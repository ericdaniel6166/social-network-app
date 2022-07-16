package com.example.socialnetworkapp.utils;

public final class Constants {

    public static final String MAIL_TEMPLATE_NAME = "mailTemplate";

    public static final String MAIL_VARIABLE_NAME = "message";

    public static final String VERIFICATION_EMAIL = "verification@socialnetworkapp.com";

    //TODO move to master_general_parameter
    public static final String VERIFICATION_EMAIL_SUBJECT = "Please verify your email address";

    //TODO move to master_general_parameter
    public static final String VERIFICATION_URL = "http://localhost:8080/auth/verifyAccount/";

    public static final String SORT_DIRECTION_ASC = "ASC";

    public static final String SORT_DIRECTION_DESC = "DESC";

    public static final String PAGE_REQUEST_SIZE_DEFAULT = "10";

    public static final String PAGE_REQUEST_PAGE_NUMBER_DEFAULT = "0";

    public static final String PAGE_REQUEST_PROPERTIES_LAST_MODIFIED_DATE = "lastModifiedDate";

    public static final String FORUM = "Forum";

    public static final String POST = "Post";

    public static final String Comment = "Comment";

    public static final String EMAIL = "Email";

    public static final String USERNAME = "Username";

    public static final String SEMICOLON = ";";

    public static final String TRUE = "True";

    public static final String FALSE = "False";

    public static final String REGEX_MASKING_EMAIL = "(?<=.)[^@](?=[^@]*?[^@]@)|(?:(?<=@.)|(?!^)\\G(?=[^@]*$)).(?=.*[^@]\\.)";

    public static final String CHARACTER_ASTERISK = "*";

    public static final String IS_ACTIVE_TRUE = "isActive=bool=true";

    public static final String IS_ACTIVE_FALSE = "isActive=bool=false";

    public static final String SCOPE = "scope";

    public static final String SELF = "self";
}
