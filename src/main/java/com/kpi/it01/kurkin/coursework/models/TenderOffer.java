package com.kpi.it01.kurkin.coursework.models;

public class TenderOffer {
    private String owner;
    private String text;
    private Long money;

    public TenderOffer(String owner, String text, Long money) {
        this.owner = owner;
        this.text = text;
        this.money = money;
    }

    public String getOwner() {
        return owner;
    }

    public String getText() {
        return text;
    }

    public Long getMoney() {
        return money;
    }
}
