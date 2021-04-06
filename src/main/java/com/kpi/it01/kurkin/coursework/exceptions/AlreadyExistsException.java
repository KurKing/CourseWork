package com.kpi.it01.kurkin.coursework.exceptions;

public class AlreadyExistsException extends Exception {
    public AlreadyExistsException() {
    }
    public AlreadyExistsException(String message) {
        super(message);
    }
}
