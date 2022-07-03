package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.enums.AppRoleName;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.AppRole;
import com.example.socialnetworkapp.repository.RoleRepository;
import com.example.socialnetworkapp.service.RoleService;
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
        log.info("Find app role by role name, role name: {}", roleName);
        return roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new ResourceNotFoundException("Role name " + roleName));
    }
}
