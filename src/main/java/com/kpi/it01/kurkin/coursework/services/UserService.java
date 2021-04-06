package com.kpi.it01.kurkin.coursework.services;

import com.kpi.it01.kurkin.coursework.dal.DataBase;
import com.kpi.it01.kurkin.coursework.exceptions.AlreadySignUpException;
import com.kpi.it01.kurkin.coursework.exceptions.IncorrectPasswordException;
import com.kpi.it01.kurkin.coursework.exceptions.NotSignUpException;
import com.kpi.it01.kurkin.coursework.exceptions.PasswordMismatchException;
import com.kpi.it01.kurkin.coursework.models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService extends Service {
    private DataBase db;

    public UserService(DataBase db) {
        this.db = db;
    }

    public User logIn(String login, String password)
            throws IncorrectPasswordException, NotSignUpException, NoSuchAlgorithmException,
            NullPointerException, IllegalArgumentException {
        login = getValidatedString(login, "Login");
        User user = db.getUserByLogin(login);

        password = getValidatedString(password, "Password");
        password = getHash(password);

        if (!user.getPasswordHash().equals(password)) {
            throw new IncorrectPasswordException("Incorrect password!");
        }

        return user;
    }

    public void signUp(String login, String name, String password, String password2)
            throws PasswordMismatchException, AlreadySignUpException, NoSuchAlgorithmException,
            IllegalArgumentException, NullPointerException {
        login = getValidatedString(login, "Login");
        name = getValidatedString(name, "Name");
        password = getValidatedString(password, "Password");
        password2 = getValidatedString(password2, "Retyped password");

        if (!password.equals(password2)){
            throw new PasswordMismatchException("Password mismatch!");
        }

        db.setUser(
                new User(name, login, getHash(password))
        );
    }

    private String getHash(String passwordToHash) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(passwordToHash.getBytes());
        byte[] bytes = md.digest();

        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}

