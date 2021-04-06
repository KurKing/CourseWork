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

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getName().equals(user.getName())) return false;
        if (!getLogin().equals(user.getLogin())) return false;
        return getPasswordHash().equals(user.getPasswordHash());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getLogin().hashCode();
        result = 31 * result + getPasswordHash().hashCode();
        return result;
    }
}
