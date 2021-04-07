package com.kpi.it01.kurkin.coursework.exceptions;

public class PasswordMismatchException extends Exception {
    public PasswordMismatchException() {
    }

    public PasswordMismatchException(String message) {
        super(message);
    }
}
