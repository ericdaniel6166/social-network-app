package com.example.socialnetworkapp.enums;

public enum MessageEnum {

    MESSAGE_UPDATE_USER_PROFILE_SUCCESS("Update profile successfully","You have successfully updated your profile");

    private final String title;
    private final String message;

    MessageEnum(String title,String message){
        this.title = title;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

}
