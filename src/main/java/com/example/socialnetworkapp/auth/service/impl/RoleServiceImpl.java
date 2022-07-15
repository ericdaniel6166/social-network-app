package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.auth.enums.RoleEnum;
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
    public AppRole findByRoleName(RoleEnum roleEnum) throws ResourceNotFoundException {
        log.debug("Find app role by role enum, role enum: {}", roleEnum);
        return roleRepository.findByRoleName(roleEnum).orElseThrow(
                () -> new ResourceNotFoundException("Role enum " + roleEnum));
    }
}
