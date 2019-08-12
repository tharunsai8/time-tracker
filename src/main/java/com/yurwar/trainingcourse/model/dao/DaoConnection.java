package com.yurwar.trainingcourse.model.dao;

import java.sql.Connection;

/**
 * Wrapper over connection class that allows to handle transaction
 */
public interface DaoConnection extends AutoCloseable {
    /**
     * Begin transaction
     */
    void beginTransaction();

    /**
     * Commit transaction
     */
    void commit();

    /**
     * Rollback transaction
     */
    void rollback();

    /**
     * Close connection
     */
    @Override
    void close();

    /**
     * @return wrapped up connection
     */
    Connection getConnection();
}
