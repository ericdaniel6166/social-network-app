package com.example.socialnetworkapp.service;


import com.example.socialnetworkapp.enums.AppRoleName;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.AppRole;

public interface RoleService {

    AppRole findByRoleName(AppRoleName roleName) throws ResourceNotFoundException;

}
