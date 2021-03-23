package com.kpi.it01.kurkin.coursework.dao;

import com.kpi.it01.kurkin.coursework.exceptions.NotSignUpException;
import com.kpi.it01.kurkin.coursework.models.User;

public interface DataBase {
    public User getUserByLogin(String login) throws NotSignUpException;
}
