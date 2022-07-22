package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.AbstractServiceTest;
import com.example.socialnetworkapp.auth.AuthTestUtils;
import com.example.socialnetworkapp.auth.model.UserProfileInfo;
import com.example.socialnetworkapp.auth.repository.UserProfileInfoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class UserProfileInfoServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    private UserProfileInfoServiceImpl userProfileInfoService;

    @Mock
    private UserProfileInfoRepository userProfileInfoRepository;

    @Test
    void saveAndFlush() {
        UserProfileInfo expected = AuthTestUtils.buildUserProfileInfo();
        Mockito.when(userProfileInfoRepository.saveAndFlush(expected)).thenReturn(expected);

        UserProfileInfo actual = userProfileInfoService.saveAndFlush(expected);

        Assertions.assertEquals(expected, actual);
    }
}