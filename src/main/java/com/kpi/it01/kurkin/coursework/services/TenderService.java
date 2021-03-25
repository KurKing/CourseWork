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
}
