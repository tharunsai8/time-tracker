package com.yurwar.trainingcourse.util.exception;

/**
 * Runtime exception that thrown in case new username already exists in database
 *
 * @author Yurii Matora
 */
public class UsernameNotUniqueException extends RuntimeException {
    public UsernameNotUniqueException() {
        super();
    }

    public UsernameNotUniqueException(String message) {
        super(message);
    }

    public UsernameNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameNotUniqueException(Throwable cause) {
        super(cause);
    }
}
