package com.seek.clientmanager.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfiguration {

    @Value("${spring.flyway.url}")
    private String flywayUrl;

    @Value("${spring.flyway.user}")
    private String flywayUser;

    @Value("${spring.flyway.password}")
    private String flywayPassword;

    @Value("${spring.flyway.schemas}")
    private String flywaySchemas;

    @Value("${spring.flyway.locations}")
    private String flywayLocations;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(flywayUrl, flywayUser, flywayPassword)
                .locations(flywayLocations)
                .load();
    }
}
