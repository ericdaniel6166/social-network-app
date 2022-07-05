package com.example.socialnetworkapp.configuration;

import com.example.socialnetworkapp.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    private static final String APPLICATION_NAME_DEFAULT = "SYSTEM";

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public Optional<String> getCurrentAuditor() {
        if (StringUtils.isBlank(applicationName)) {
            applicationName = APPLICATION_NAME_DEFAULT;
        }
        String username;
        if (StringUtils.isNotBlank(CommonUtils.getUsername())) {
            username = CommonUtils.getUsername();
        } else {
            username = applicationName;
        }
        return Optional.of(username);
    }
}
