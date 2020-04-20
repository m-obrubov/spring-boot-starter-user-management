package com.github.mobrubov.usermanagement.common.exception;

/**
 * @author Максим
 * Created on 24.03.2020
 */
public class UserManagementException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserManagementException() {
        this.errorCode = ErrorCode.UNKNOWN;
    }

    public UserManagementException(String message) {
        super(message);
        this.errorCode = ErrorCode.UNKNOWN;
    }

    public UserManagementException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = ErrorCode.UNKNOWN;
    }

    public UserManagementException(Throwable cause) {
        super(cause);
        this.errorCode = ErrorCode.UNKNOWN;
    }

    public UserManagementException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = ErrorCode.UNKNOWN;
    }

    public UserManagementException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public UserManagementException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public UserManagementException(
        String message,
        ErrorCode errorCode,
        Throwable cause
    ) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public UserManagementException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public UserManagementException(
        String message,
        ErrorCode errorCode,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public final ErrorCode getErrorCode() {
        return errorCode;
    }
}
