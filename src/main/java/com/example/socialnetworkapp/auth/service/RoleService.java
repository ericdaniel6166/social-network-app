package com.example.socialnetworkapp.auth.service;


import com.example.socialnetworkapp.auth.enums.AppRoleName;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.auth.model.AppRole;

public interface RoleService {

    AppRole findByRoleName(AppRoleName roleName) throws ResourceNotFoundException;

}
