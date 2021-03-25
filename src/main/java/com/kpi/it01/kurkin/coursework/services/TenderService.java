package com.kpi.it01.kurkin.coursework.services;

import com.kpi.it01.kurkin.coursework.dal.DataBase;
import com.kpi.it01.kurkin.coursework.models.Tender;

import java.util.ArrayList;
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

    public void disableTender(String tenderId) {
        db.setTenderData(tenderId, "isActive", false);
    }

    public void activateTender(String tenderId) {
        db.setTenderData(tenderId, "isActive", true);
    }

    // TODO creating tender
    public void createNewTender() {
        Tender tender = new Tender(
                "lyosha.kurkin@gmail.com",
                "Text about this tender",
                new ArrayList<>(),
                "id",
                "Tender name"
        );
        db.createTender(tender);
    }

    //TODO creating offer
}
