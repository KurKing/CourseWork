package com.kpi.it01.kurkin.coursework.dao.interfaces;

public interface DaoFactory {
    TenderDao getTenderDao();
    TenderOfferDao getTenderOfferDao();
    UserDao getUserDao();
}
