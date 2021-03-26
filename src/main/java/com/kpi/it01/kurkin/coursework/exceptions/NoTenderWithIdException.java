package com.kpi.it01.kurkin.coursework.exceptions;

public class NoTenderWithIdException extends Exception{
    public NoTenderWithIdException() {
    }

    public NoTenderWithIdException(String message) {
        super(message);
    }

    public NoTenderWithIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTenderWithIdException(Throwable cause) {
        super(cause);
    }

    public NoTenderWithIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
