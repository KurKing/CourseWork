package com.kpi.it01.kurkin.coursework.dal;

import com.kpi.it01.kurkin.coursework.exceptions.AlreadySignUpException;
import com.kpi.it01.kurkin.coursework.exceptions.NoTenderWithIdException;
import com.kpi.it01.kurkin.coursework.exceptions.NotSignUpException;
import com.kpi.it01.kurkin.coursework.models.Tender;
import com.kpi.it01.kurkin.coursework.models.TenderOffer;
import com.kpi.it01.kurkin.coursework.models.User;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public interface DataBase {
    // For User
    public User getUserByLogin(String login) throws NotSignUpException;
    public void setUser(User user) throws AlreadySignUpException;

    // For tenders
    public ArrayList<Tender> getTendersWithOwner(String owner) throws ExecutionException, InterruptedException;
    public ArrayList<Tender> getTenders() throws ExecutionException, InterruptedException;
    public ArrayList<Tender> getTendersByName(String name) throws ExecutionException, InterruptedException;
    public Tender getTenderWithId(String id) throws NoTenderWithIdException, ExecutionException, InterruptedException;
    public String getTenderOwner(String tenderId) throws NoTenderWithIdException, ExecutionException, InterruptedException;

    public void updateTenderData(String tenderId, String name, Object value);

    public void deleteTender(String tenderId);
    public void createTender(Tender newTender);
    public void createOffer(TenderOffer newOffer);

}
