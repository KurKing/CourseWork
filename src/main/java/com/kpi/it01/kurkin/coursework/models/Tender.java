package com.kpi.it01.kurkin.coursework.models;

import java.util.ArrayList;

public class Tender {
    private String owner;
    private String about;
    private ArrayList<TenderOffer> offers;
    private String id;
    private String name;

    public Tender(String owner, String about, ArrayList<TenderOffer> offers, String id, String name) {
        this.owner = owner;
        this.about = about;
        this.offers = offers;
        this.id = id;
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public String getAbout() {
        return about;
    }

    public ArrayList<TenderOffer> getOffers() {
        return offers;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
