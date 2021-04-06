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
     User getUserByLogin(String login) throws NotSignUpException;
     void setUser(User user) throws AlreadySignUpException;

     ArrayList<Tender> getTendersWithOwner(String owner) throws ExecutionException, InterruptedException;
     ArrayList<Tender> getTenders() throws ExecutionException, InterruptedException;
     ArrayList<Tender> getTendersByName(String name) throws ExecutionException, InterruptedException;
     Tender getTenderWithId(String id) throws NoTenderWithIdException, ExecutionException, InterruptedException;
     String getTenderOwner(String tenderId) throws NoTenderWithIdException, ExecutionException, InterruptedException;

     void updateTenderData(String tenderId, String name, Object value);

     void deleteTender(String tenderId);
     void createTender(Tender newTender);
     void createOffer(TenderOffer newOffer);
}
