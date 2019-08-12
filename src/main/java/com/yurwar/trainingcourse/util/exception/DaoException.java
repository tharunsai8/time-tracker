package com.yurwar.trainingcourse.util.exception;

/**
 * Runtime exception that thrown in dao layer to wrap up SQLException
 *
 * @see java.sql.SQLException
 */
public class DaoException extends RuntimeException {
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
