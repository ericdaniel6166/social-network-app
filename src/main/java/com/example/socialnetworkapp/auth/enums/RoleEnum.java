package com.example.socialnetworkapp.auth.enums;

public enum RoleEnum {
    ROLE_USER("SCOPE_ROLE_USER"),
    ROLE_MODERATOR("SCOPE_ROLE_MODERATOR"),
    ROLE_ADMIN("SCOPE_ROLE_ADMIN"),
    ROLE_ROOT_ADMIN("SCOPE_ROLE_ROOT_ADMIN");


    private String scopeRole;

    RoleEnum(String scopeRole) {
        this.scopeRole = scopeRole;
    }


    public String getScopeRole() {
        return this.scopeRole;
    }

}
