package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.enums.AppRoleName;
import com.example.socialnetworkapp.auth.model.AppRole;
import com.example.socialnetworkapp.auth.repository.RoleRepository;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

class RoleServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenFindByRoleName_givenNotEmptyAppRole_thenReturnAppRole() throws ResourceNotFoundException {
        AppRole expected = AuthTestUtils.buildAppRole(AppRoleName.ROLE_USER);
        Mockito.when(roleRepository.findByRoleName(expected.getRoleName())).thenReturn(Optional.of(expected));

        AppRole actual = roleService.findByRoleName(expected.getRoleName());

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void whenFindByRoleName_givenEmptyAppRole_thenThrowResourceNotFoundException() {
        AppRoleName roleName = AppRoleName.ROLE_USER;
        Mockito.when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.empty());
        ResourceNotFoundException expected = new ResourceNotFoundException("Role name " + roleName);

        try {
            roleService.findByRoleName(roleName);
        } catch (ResourceNotFoundException e) {
            Assertions.assertEquals(expected, e);
        }

    }


}