package com.kpi.it01.kurkin.coursework.models;

public class TenderOffer {
    private String owner;
    private String text;
    private int money;
    private String tenderId;

    public TenderOffer(String owner, String text, int money, String id) {
        this.owner = owner;
        this.text = text;
        this.money = money;
        this.tenderId = id;
    }

    public TenderOffer(String owner, String text, Long money, String id) {
        this.owner = owner;
        this.text = text;
        this.money = Math.toIntExact(money);
        this.tenderId = id;
    }

    public TenderOffer(String owner, String text, String money, String id) {
        this.owner = owner;
        this.text = text;
        this.money = Integer.parseInt(money);
        this.tenderId = id;
    }

    public String getTenderId() {
        return tenderId;
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
