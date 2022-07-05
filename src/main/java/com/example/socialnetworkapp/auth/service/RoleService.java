package com.example.socialnetworkapp.auth.service;


import com.example.socialnetworkapp.auth.enums.AppRoleName;
import com.example.socialnetworkapp.auth.model.AppRole;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;

public interface RoleService {

    AppRole findByRoleName(AppRoleName roleName) throws ResourceNotFoundException;

}
