package com.kpi.it01.kurkin.coursework.services;

import com.kpi.it01.kurkin.coursework.dao.DataBase;
import com.kpi.it01.kurkin.coursework.exceptions.AlreadySignUpException;
import com.kpi.it01.kurkin.coursework.exceptions.IncorrectPasswordException;
import com.kpi.it01.kurkin.coursework.exceptions.NotSignUpException;
import com.kpi.it01.kurkin.coursework.exceptions.PasswordMissmatchException;
import com.kpi.it01.kurkin.coursework.models.User;
import com.kpi.it01.kurkin.coursework.utils.PasswordHasher;

import java.security.NoSuchAlgorithmException;

public class UserService {
    private DataBase db;

    public UserService(DataBase db) {
        this.db = db;
    }

    public boolean isLogedIn(String login){
        return false;
    }

    public User logIn(String login, String password) throws IncorrectPasswordException, NotSignUpException {
        try {
            password = PasswordHasher.getHash(password);
            System.out.println(password);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getLocalizedMessage());
        }

        User user = db.getUserByLogin(login);

        if (user.comparePassword(password)) {
            throw new IncorrectPasswordException();
        }

        return user;
    }

    public void logOut(String login) {

    }

    public void signUp(String login, String name, String password, String password2) throws PasswordMissmatchException, AlreadySignUpException {

    }
}

