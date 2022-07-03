package com.example.socialnetworkapp.configuration;

import brave.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
@ConditionalOnClass(value = Tracer.class)
@RequiredArgsConstructor
public class SleuthConfiguration implements OperationIdConfiguration {

    private final Tracer tracer;

    @Override
    public String getOperationId() {
        return Objects.requireNonNull(tracer.currentSpan()).context().traceIdString();
    }
}
