package com.kpi.it01.kurkin.coursework.models;

import java.util.ArrayList;

public class Tender {
    private String owner;
    private String about;
    private ArrayList<TenderOffer> offers;
    private String id;
    private String name;
    private boolean isActive;

    public Tender(String owner, String about, ArrayList<TenderOffer> offers, String id, String name, boolean isActive) {
        this.owner = owner;
        this.about = about;
        this.offers = offers;
        this.id = id;
        this.name = name;
        this.isActive = isActive;
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

    public boolean isActive() {
        return isActive;
    }

    public boolean isOwner(String other) {
        return owner.equals(other);
    }

    @Override
    public String toString() {
        return "Tender{" +
                "owner='" + owner + '\'' +
                ", about='" + about + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
