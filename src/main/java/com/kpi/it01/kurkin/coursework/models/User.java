package com.kpi.it01.kurkin.coursework.models;

public class User {
    private String name;
    private String login;
    private String passwordHash;

    public User(String name, String login, String passwordHash) {
        this.name = name;
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public boolean comparePassword(String passwordHash) {
        return  passwordHash.equals(this.passwordHash);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
