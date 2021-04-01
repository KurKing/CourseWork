package com.kpi.it01.kurkin.coursework.services;

public class Service {
    protected String getValidatedString(String stringToValidate, String stringName) throws IllegalArgumentException, NullPointerException {
        if (stringToValidate == null) { throw new NullPointerException(stringName+" is required!"); }
        String resultString = stringToValidate.trim();
        if (resultString.isEmpty()) { throw new IllegalArgumentException(stringName+" is required!"); }
        return resultString;
    }
}
