package com.kpi.it01.kurkin.coursework.dao.firebase;

import com.google.cloud.firestore.Firestore;
import com.kpi.it01.kurkin.coursework.dao.interfaces.TenderOfferDao;
import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.exceptions.NoIdException;
import com.kpi.it01.kurkin.coursework.models.TenderOffer;

import java.util.List;

public class TenderOfferFirebaseDao implements TenderOfferDao {

    private final Firestore db;

    public TenderOfferFirebaseDao(Firestore db) {
        this.db = db;
    }

    @Override
    public void create(TenderOffer offer) throws DataBaseErrorException {

    }

    @Override
    public List<TenderOffer> getAllFromTenderWithId(String id) throws DataBaseErrorException, NoIdException {
        return null;
    }
}
