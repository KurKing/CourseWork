package com.kpi.it01.kurkin.coursework.models;

public class TenderOffer {
    private String owner;
    private String text;
    private int money;

    public TenderOffer(String owner, String text, Long money) {
        this.owner = owner;
        this.text = text;
        this.money = Math.toIntExact(money);
    }

    public TenderOffer(String owner, String text, int money) {
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

    public int getMoney() {
        return money;
    }
}
