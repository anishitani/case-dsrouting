package com.github.anishitani.dsrouting.repository.jooq;

import com.github.anishitani.dsrouting.infra.datasource.ExceptionTranslator;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

public class BaseJooqRepository {
    protected final DefaultConfiguration configuration;

    public BaseJooqRepository(DataSource dataSource) {
        DataSourceConnectionProvider connectionProvider
                = new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
        this.configuration = new DefaultConfiguration();
        configuration.setSQLDialect(SQLDialect.POSTGRES);
        configuration.setConnectionProvider(connectionProvider);
        configuration.setExecuteListenerProvider(new DefaultExecuteListenerProvider(new ExceptionTranslator()));
    }
}
