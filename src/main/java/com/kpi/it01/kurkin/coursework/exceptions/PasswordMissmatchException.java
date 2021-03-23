package com.kpi.it01.kurkin.coursework.exceptions;

public class PasswordMissmatchException extends Exception {
    public PasswordMissmatchException() {
    }

    public PasswordMissmatchException(String message) {
        super(message);
    }

    public PasswordMissmatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordMissmatchException(Throwable cause) {
        super(cause);
    }

    public PasswordMissmatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
