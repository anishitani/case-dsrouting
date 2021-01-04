package com.github.anishitani.dsrouting.infra.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TransactionRoutingDataSource extends AbstractRoutingDataSource {
    public enum DataSourceType {
        READ_WRITE,
        READ_ONLY
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? DataSourceType.READ_ONLY
                : DataSourceType.READ_WRITE;
    }
}
