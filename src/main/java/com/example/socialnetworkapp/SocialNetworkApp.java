package com.example.socialnetworkapp;

import com.example.socialnetworkapp.configuration.FlywayConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.util.Optional;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class SocialNetworkApp implements CommandLineRunner {

    private static final String SCHEMA_HISTORY = "schema_history";

    private static final String FLYWAY_BASELINE_VERSION_DEFAULT = "0.0";

    private final DataSource dataSource;

    private final FlywayConfiguration flywayConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (flywayConfiguration.isActive()) {
            Flyway.configure().dataSource(dataSource)
                    .baselineOnMigrate(flywayConfiguration.isBaselineOnMigrate())
                    .locations(flywayConfiguration.getLocations())
                    .table(Optional.ofNullable(flywayConfiguration.getTable()).orElse(SCHEMA_HISTORY))
                    .baselineVersion(Optional.ofNullable(flywayConfiguration.getBaselineVersion()).orElse(FLYWAY_BASELINE_VERSION_DEFAULT))
                    .load()
                    .migrate();
        }
    }
}
