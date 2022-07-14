package com.example.socialnetworkapp.auth.enums;

public enum ScopeRole {
    SCOPE_ROLE_ADMIN(3),
    SCOPE_ROLE_MODERATOR(2),
    SCOPE_ROLE_USER(1),
    ROLE_ANONYMOUS(0);

    private Integer value;


    ScopeRole(int value) {
        this.value = value;
    }

    public Integer getValue(){
        return this.value;
    }


}
