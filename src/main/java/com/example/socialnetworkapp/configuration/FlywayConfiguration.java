package com.example.socialnetworkapp.configuration;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
@ConditionalOnProperty(prefix = "spring.flyway", name = "enabled")
@ConfigurationProperties("spring.flyway")
@Data
public class FlywayConfiguration {

    private static final String SCHEMA_HISTORY = "schema_history";

    private String locations;
    private String baselineVersion;
    private boolean baselineOnMigrate;
    private String table;
    private String driverVendor;

    @Bean
    public Flyway flyway(DataSource dataSource) {
        FluentConfiguration fluentConfiguration = Flyway.configure();

        fluentConfiguration.dataSource(dataSource)
                .baselineOnMigrate(baselineOnMigrate)
                .locations(locations + driverVendor)
                .table(Optional.ofNullable(table).orElse(SCHEMA_HISTORY));

        if (StringUtils.isNotBlank(baselineVersion)) {
            fluentConfiguration.baselineVersion(baselineVersion);
        }

        return fluentConfiguration.load();
    }

    @Bean
    public FlywayMigrationInitializer flywayMigrationInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway);
    }


}
