package com.yurwar.trainingcourse.model.dao.impl;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

class ConnectionPoolHolder {
    private static volatile DataSource dataSource;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    PGSimpleDataSource ds = new PGSimpleDataSource();
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
