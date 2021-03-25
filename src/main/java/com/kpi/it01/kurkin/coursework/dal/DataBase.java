package com.kpi.it01.kurkin.coursework.dal;

import com.kpi.it01.kurkin.coursework.exceptions.AlreadySignUpException;
import com.kpi.it01.kurkin.coursework.exceptions.NotSignUpException;
import com.kpi.it01.kurkin.coursework.models.Tender;
import com.kpi.it01.kurkin.coursework.models.User;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public interface DataBase {
    public User getUserByLogin(String login) throws NotSignUpException;
    public void setUser(User user) throws AlreadySignUpException;

    public ArrayList<Tender> getTenders(String owner) throws ExecutionException, InterruptedException;
    public ArrayList<Tender> getTenders() throws ExecutionException, InterruptedException;

    public void activateTender(String tenderId);
    public void disableTender(String tenderId);

    public void deleteTender(String tenderId);

    public void createTender(Tender newTender);

}
