package com.kpi.it01.kurkin.coursework.dao.firebase;

import com.google.cloud.firestore.Firestore;
import com.kpi.it01.kurkin.coursework.dao.interfaces.DaoFactory;
import com.kpi.it01.kurkin.coursework.dao.interfaces.TenderDao;
import com.kpi.it01.kurkin.coursework.dao.interfaces.TenderOfferDao;
import com.kpi.it01.kurkin.coursework.dao.interfaces.UserDao;

public class FirebaseDaoFactory implements DaoFactory {

    private UserDao userDao;
    private TenderOfferDao tenderOfferDao;
    private TenderDao tenderDao;

    public FirebaseDaoFactory(Firestore db) {
        userDao = new UserFirebaseDao(db);
        tenderOfferDao = new TenderOfferFirebaseDao(db);
        tenderDao = new TenderFirebaseDao(db, tenderOfferDao);
    }

    @Override
    public TenderDao getTenderDao() {
        return tenderDao;
    }

    @Override
    public TenderOfferDao getTenderOfferDao() {
        return tenderOfferDao;
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }
}
