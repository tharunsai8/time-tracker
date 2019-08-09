package com.yurwar.trainingcourse.model.dao.impl;

import org.postgresql.ds.PGConnectionPoolDataSource;

import javax.sql.ConnectionPoolDataSource;
import java.util.ResourceBundle;

class ConnectionPoolHolder {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
    private static volatile ConnectionPoolDataSource dataSource;

    static ConnectionPoolDataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    PGConnectionPoolDataSource ds = new PGConnectionPoolDataSource();
                    ds.setUrl(resourceBundle.getString("database.url"));
                    ds.setUser(resourceBundle.getString("database.user"));
                    ds.setPassword(resourceBundle.getString("database.password"));
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }
}
