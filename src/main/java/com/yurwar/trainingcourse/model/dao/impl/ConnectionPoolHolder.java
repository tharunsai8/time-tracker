package com.yurwar.trainingcourse.model.dao.impl;

import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.osgi.PGDataSourceFactory;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        URL = resourceBundle.getString("database.url");
        USER = resourceBundle.getString("database.user");
        PASSWORD = resourceBundle.getString("database.password");
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    PGSimpleDataSource ds = new PGSimpleDataSource();
                    ds.setUrl(URL);
                    ds.setUser(USER);
                    ds.setPassword(PASSWORD);
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }
}
