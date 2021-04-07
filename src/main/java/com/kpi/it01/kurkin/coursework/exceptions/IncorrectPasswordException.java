package com.kpi.it01.kurkin.coursework.exceptions;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException() {
    }
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
