package com.yurwar.trainingcourse.util.exception;

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
