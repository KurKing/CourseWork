package com.kpi.it01.kurkin.coursework.dal;

import com.kpi.it01.kurkin.coursework.exceptions.AlreadySignUpException;
import com.kpi.it01.kurkin.coursework.exceptions.NotSignUpException;
import com.kpi.it01.kurkin.coursework.models.User;

public interface DataBase {
    public User getUserByLogin(String login) throws NotSignUpException;
    public void setUser(User user) throws AlreadySignUpException;

}
