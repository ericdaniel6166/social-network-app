package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.auth.enums.AppRoleName;
import com.example.socialnetworkapp.auth.model.AppRole;
import com.example.socialnetworkapp.auth.repository.RoleRepository;
import com.example.socialnetworkapp.auth.service.RoleService;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public AppRole findByRoleName(AppRoleName roleName) throws ResourceNotFoundException {
        log.debug("Find app role by role name, role name: {}", roleName);
        return roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new ResourceNotFoundException("Role name " + roleName));
    }
}
