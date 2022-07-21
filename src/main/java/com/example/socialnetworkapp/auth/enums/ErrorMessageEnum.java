package com.example.socialnetworkapp.auth.enums;

public enum ErrorMessageEnum {
    ERROR_MESSAGE_SET_ROLE_YOURSELF("You can't set role to yourself"),

    ERROR_MESSAGE_NOT_HAVE_PERMISSION_SET_ROLE_THIS_USER("You don't have permission to set role to this user"),

    ERROR_MESSAGE_NOT_HAVE_PERMISSION_SET_THIS_ROLE("You don't have permission to set this role"),

    ERROR_MESSAGE_USER_ALREADY_HAD_THIS_ROLE("This user has already had this role"),

    ERROR_MESSAGE_NOT_HAVE_PERMISSION_SEARCH_INACTIVE_RESOURCE("User does not have permission to search inactive resource");

    private final String errorMessage;

    ErrorMessageEnum(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
