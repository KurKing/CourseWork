package com.kpi.it01.kurkin.coursework.services;

import com.kpi.it01.kurkin.coursework.dal.DataBase;
import com.kpi.it01.kurkin.coursework.exceptions.AlreadySignUpException;
import com.kpi.it01.kurkin.coursework.exceptions.IncorrectPasswordException;
import com.kpi.it01.kurkin.coursework.exceptions.NotSignUpException;
import com.kpi.it01.kurkin.coursework.exceptions.PasswordMismatchException;
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

    public User logIn(String login, String password) throws IncorrectPasswordException, NotSignUpException, NoSuchAlgorithmException {

        password = PasswordHasher.getHash(password);
        User user = db.getUserByLogin(login);

        if (!user.comparePassword(password)) {
            throw new IncorrectPasswordException();
        }

        return user;
    }

    public void signUp(String login, String name, String password, String password2) throws PasswordMismatchException, AlreadySignUpException, NoSuchAlgorithmException {
        if (!password.equals(password2)){
            throw new PasswordMismatchException();
        }

        db.setUser(
                new User(name, login, PasswordHasher.getHash(password))
        );

    }
}

