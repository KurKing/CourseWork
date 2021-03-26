package com.kpi.it01.kurkin.coursework.services;

import com.kpi.it01.kurkin.coursework.dal.DataBase;
import com.kpi.it01.kurkin.coursework.exceptions.NoTenderWithIdException;
import com.kpi.it01.kurkin.coursework.models.Tender;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class TenderService {
    private DataBase db;

    public TenderService(DataBase db) {
        this.db = db;
    }

    public ArrayList<Tender> getTenders() {
        try {
           return db.getTenders();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<Tender>();
    }

    public ArrayList<Tender> getTenders(String owner) {
        try {
            return db.getTenders(owner);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<Tender>();
    }

    public Tender getTenderWithId(String id) throws IllegalArgumentException, NoTenderWithIdException {

        if (id.isEmpty()) { throw new IllegalArgumentException(); }

        try {
            return db.getTenderWithId(id);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void disableTender(String tenderId) {
        db.setTenderData(tenderId, "isActive", false);
    }

    public void activateTender(String tenderId) {
        db.setTenderData(tenderId, "isActive", true);
    }

    public void createNewTender(String name, String owner, String about) {
        Tender tender = new Tender(
                owner,
                about,
                null,
                "",
                name,
                true
        );
        db.createTender(tender);
    }

    //TODO creating offer
}
