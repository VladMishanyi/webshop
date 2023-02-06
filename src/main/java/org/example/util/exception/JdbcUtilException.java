package org.example.util.exception;

public class JdbcUtilException extends RuntimeException {
    public JdbcUtilException(String message, Throwable cause) {
        super(message, cause);
    }
}
