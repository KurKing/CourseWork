package com.kpi.it01.kurkin.coursework.exceptions;

public class NotOwnerException extends Exception{
    public NotOwnerException() {
    }

    public NotOwnerException(String message) {
        super(message);
    }
}
