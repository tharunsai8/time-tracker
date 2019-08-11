package com.yurwar.trainingcourse.model.dao.impl;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

class ConnectionPoolHolder {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
    private static volatile DataSource dataSource;

    static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl(resourceBundle.getString("database.url"));
                    ds.setUsername(resourceBundle.getString("database.user"));
                    ds.setPassword(resourceBundle.getString("database.password"));
                    ds.setDriverClassName(resourceBundle.getString("database.driver"));
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }
}
