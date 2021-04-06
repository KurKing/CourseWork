package com.kpi.it01.kurkin.coursework.dao.interfaces;

import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.exceptions.NoIdException;
import com.kpi.it01.kurkin.coursework.models.TenderOffer;

import java.util.List;

public interface TenderOfferDao {
    void create(TenderOffer offer) throws DataBaseErrorException;
    List<TenderOffer> getAllFromTenderWithId(String id) throws DataBaseErrorException, NoIdException;
}
