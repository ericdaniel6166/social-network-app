package com.example.socialnetworkapp.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
@ConditionalOnClass(value = Tracer.class)
public class SleuthConfiguration implements OperationIdConfiguration {

    @Autowired
    private Tracer tracer;

    @Override
    public String getOperationId() {
        return Objects.requireNonNull(tracer.currentSpan()).context().traceId();
    }
}
