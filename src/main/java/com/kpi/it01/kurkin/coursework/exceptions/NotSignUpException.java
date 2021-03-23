package com.kpi.it01.kurkin.coursework.exceptions;

public class NotSignUpException extends Exception {
    public NotSignUpException() {
    }

    public NotSignUpException(String message) {
        super(message);
    }

    public NotSignUpException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSignUpException(Throwable cause) {
        super(cause);
    }

    public NotSignUpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
