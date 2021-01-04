package com.github.anishitani.dsrouting.infra;

import com.github.anishitani.dsrouting.infra.datasource.ExceptionTranslator;
import com.github.anishitani.dsrouting.infra.datasource.TransactionRoutingDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

    @ConfigurationProperties("jdbc.ro")
    @Bean("roConfig")
    HikariConfig roConfig() {
        return new HikariConfig();
    }

    @ConfigurationProperties("jdbc.rw")
    @Bean("rwConfig")
    HikariConfig rwConfig() {
        return new HikariConfig();
    }

    @Bean
    DataSourceTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    DataSourceConnectionProvider connectionProvider(DataSource dataSource){
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    DefaultDSLContext defaultDSLContext(DataSource dataSource){
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.setSQLDialect(SQLDialect.POSTGRES);
        configuration.setConnectionProvider(connectionProvider(dataSource));
        configuration.setExecuteListenerProvider(new DefaultExecuteListenerProvider(new ExceptionTranslator()));
        return new DefaultDSLContext(configuration);
    }

    @Bean
    TransactionRoutingDataSource dataSourceRouter(
            @Qualifier("roConfig") HikariConfig roConfig,
            @Qualifier("rwConfig") HikariConfig rwConfig
    ) {
        TransactionRoutingDataSource router = new TransactionRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(
                TransactionRoutingDataSource.DataSourceType.READ_ONLY,
                new HikariDataSource(roConfig)
        );
        dataSourceMap.put(
                TransactionRoutingDataSource.DataSourceType.READ_WRITE,
                new HikariDataSource(rwConfig)
        );

        router.setTargetDataSources(dataSourceMap);
        return router;
    }
}
