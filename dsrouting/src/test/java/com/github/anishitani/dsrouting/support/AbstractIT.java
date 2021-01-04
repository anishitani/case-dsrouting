package com.github.anishitani.dsrouting.support;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractIT {
    private static final String PG_13_ALPINE = "postgres:13-alpine";
    static final PostgreSQLContainer postgres = new PostgreSQLContainer(PG_13_ALPINE);

    static {
        postgres.withReuse(true).start();

        Flyway.configure()
                .configuration(new ClassicConfiguration() {{
                    setLocationsAsStrings("filesystem:../infra/sql");
                    setDataSource(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
                }})
                .load().migrate();
    }

    @DynamicPropertySource
    static public void updateDynamicPropertyRegistry(DynamicPropertyRegistry registry) {
        registry.add("jdbc.rw.driver-class-name", postgres::getDriverClassName);
        registry.add("jdbc.rw.jdbc-url", postgres::getJdbcUrl);
        registry.add("jdbc.rw.username", postgres::getUsername);
        registry.add("jdbc.rw.password", postgres::getPassword);

        registry.add("jdbc.ro.driver-class-name", postgres::getDriverClassName);
        registry.add("jdbc.ro.jdbc-url", postgres::getJdbcUrl);
        registry.add("jdbc.ro.username", postgres::getUsername);
        registry.add("jdbc.ro.password", postgres::getPassword);

    }
}
