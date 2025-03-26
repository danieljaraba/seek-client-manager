package com.seek.clientmanager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactoryOptions;
import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    private final Environment env;

    public R2dbcConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(DRIVER, "mysql")
                .option(HOST, env.getProperty("spring.r2dbc.host"))
                .option(PORT, Integer.parseInt(env.getProperty("spring.r2dbc.port")))
                .option(USER, env.getProperty("spring.r2dbc.username"))
                .option(PASSWORD, env.getProperty("spring.r2dbc.password"))
                .option(DATABASE, env.getProperty("spring.r2dbc.name"))
                .build());
    }
}