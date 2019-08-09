package com.yurwar.trainingcourse.model.dao;

import java.sql.Connection;

public interface DaoConnection extends AutoCloseable {
    void beginTransaction();

    void commit();

    void rollback();

    @Override
    void close();

    Connection getConnection();
}
