package com.kpi.it01.kurkin.coursework.models;

import java.util.List;

public class Tender {
    private String owner;
    private String about;
    private List<TenderOffer> offers;
    private String id;
    private String name;
    private boolean isActive;

    public Tender(String owner, String about, List<TenderOffer> offers, String id, String name, boolean isActive) {
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

    public List<TenderOffer> getOffers() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tender)) return false;

        Tender tender = (Tender) o;

        if (isActive() != tender.isActive()) return false;
        if (!getOwner().equals(tender.getOwner())) return false;
        if (!getAbout().equals(tender.getAbout())) return false;
        if (!getOffers().equals(tender.getOffers())) return false;
        if (!getId().equals(tender.getId())) return false;
        return getName().equals(tender.getName());
    }

    @Override
    public int hashCode() {
        int result = getOwner().hashCode();
        result = 31 * result + getAbout().hashCode();
        result = 31 * result + getOffers().hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (isActive() ? 1 : 0);
        return result;
    }
}
