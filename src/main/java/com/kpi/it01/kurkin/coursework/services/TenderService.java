package com.kpi.it01.kurkin.coursework.services;

import com.kpi.it01.kurkin.coursework.dal.DataBase;
import com.kpi.it01.kurkin.coursework.exceptions.NoIdException;
import com.kpi.it01.kurkin.coursework.exceptions.NoTenderWithIdException;
import com.kpi.it01.kurkin.coursework.exceptions.NotOwnerException;
import com.kpi.it01.kurkin.coursework.models.Tender;
import com.kpi.it01.kurkin.coursework.models.TenderOffer;

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

    public ArrayList<Tender> getTendersWithOwner(String owner) {
        try {
            return db.getTendersWithOwner(owner);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<Tender>();
    }

    public ArrayList<Tender> getTendersByName(String name) {
        try {
            return db.getTendersByName(name);
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

    public void setTenderStatus(String tenderId, String owner, Boolean isActive) throws InterruptedException, NoTenderWithIdException, ExecutionException, NotOwnerException {
        if (owner.equals(db.getTenderOwner(tenderId))){
            db.updateTenderData(tenderId, "isActive", isActive);
            return;
        }

        throw new NotOwnerException();
    }

    public void deleteTender(String tenderId, String owner) {
        try {
            if (owner.equals(db.getTenderOwner(tenderId))){
                db.deleteTender(tenderId);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void createNewOffer(String text, String money, String tenderId, String userLogin) throws IllegalArgumentException, NoIdException {
        text = text.trim();
        if (text.isEmpty()) {
            throw new IllegalArgumentException("Text is required");
        }
        money = money.trim();
        if (money.isEmpty()) {
            throw new IllegalArgumentException("Money is required");
        }
        tenderId = tenderId.trim();
        if (tenderId.isEmpty()) {
            throw new NoIdException();
        }

        db.createOffer(new TenderOffer(userLogin, text, money, tenderId));
    }
}
