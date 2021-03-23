package com.kpi.it01.kurkin.coursework.exceptions;

public class AlreadySignUpException extends Exception {
    public AlreadySignUpException() {
    }

    public AlreadySignUpException(String message) {
        super(message);
    }

    public AlreadySignUpException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadySignUpException(Throwable cause) {
        super(cause);
    }

    public AlreadySignUpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
