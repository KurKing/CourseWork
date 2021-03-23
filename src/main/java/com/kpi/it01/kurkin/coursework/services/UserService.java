package com.kpi.it01.kurkin.coursework.services;

import com.kpi.it01.kurkin.coursework.dao.DataBase;

public class UserService {
    private DataBase db;

    public UserService(DataBase db) {
        this.db = db;
    }

    public boolean isLogedIn(String login){
        return true;
    }

    public void logIn(String login, String password){

    }

    public void logOut(String login) {

    }

    public void signUp(String login, String name, String password, String password2) {

    }
}

