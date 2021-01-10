package com.github.anishitani.dsrouting;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Target;
import org.testcontainers.containers.PostgreSQLContainer;

public class JooqGenerationTool {
    private static String POSTGRES_ALPINE = "postgres:alpine";
    private static PostgreSQLContainer db = new PostgreSQLContainer(POSTGRES_ALPINE);

    public static void main(String args[]) throws Exception {
        db.withReuse(true).start();
        Flyway.configure()
                .configuration(new ClassicConfiguration() {{
                    setLocationsAsStrings("filesystem:../infra/sql");
                    setDataSource(db.getJdbcUrl(), db.getUsername(), db.getPassword());
                }})
                .load().migrate();
        Configuration configuration = getConfiguration();
        GenerationTool.generate(configuration);
    }

    private static Configuration getConfiguration() {


        Configuration configuration = new Configuration()
                .withJdbc(new Jdbc()
                        .withDriver(db.getDriverClassName())
                        .withUrl(db.getJdbcUrl())
                        .withUser(db.getUsername())
                        .withPassword(db.getPassword()))
                .withGenerator(new Generator()
                        .withDatabase(new Database()
                                .withName("org.jooq.meta.postgres.PostgresDatabase")
                                .withIncludes(".*")
                                .withExcludes("")
                                .withInputSchema("blog"))
                        .withTarget(new Target()
                                .withPackageName("com.github.anishitani.dsrouting.generated")
                                .withDirectory("src/main/java")));
        return configuration;
    }
}
