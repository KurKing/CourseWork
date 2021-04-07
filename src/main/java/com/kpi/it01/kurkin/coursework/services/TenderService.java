package com.kpi.it01.kurkin.coursework.services;

import com.kpi.it01.kurkin.coursework.dao.interfaces.DaoFactory;
import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.exceptions.NoIdException;
import com.kpi.it01.kurkin.coursework.exceptions.NotOwnerException;
import com.kpi.it01.kurkin.coursework.models.Tender;
import com.kpi.it01.kurkin.coursework.models.TenderOffer;

import java.util.List;


import static com.kpi.it01.kurkin.coursework.utils.Validator.getValidatedString;

public class TenderService {
    private DaoFactory daoFactory;

    public TenderService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public List<Tender> getTenders() throws DataBaseErrorException {
        return daoFactory.getTenderDao().getAll();
    }

    public List<Tender> getTendersWithOwner(String owner) throws DataBaseErrorException {
        return daoFactory.getTenderDao().getAllWithOwner(owner);
    }

    public List<Tender> getTendersByName(String name) throws DataBaseErrorException {
        return daoFactory.getTenderDao().getAllWithName(name);
    }

    public Tender getTenderWithId(String id) throws IllegalArgumentException, DataBaseErrorException, NoIdException {
        id = getValidatedString(id, "Id");
        return daoFactory.getTenderDao().get(id);
    }

    public void setTenderStatus(String tenderId, String owner, Boolean isActive)
            throws IllegalArgumentException, NotOwnerException, NoIdException, DataBaseErrorException {
        if (tenderId.isEmpty()) { throw new IllegalArgumentException(); }

        if (owner.equals(daoFactory.getTenderDao().getTenderOwner(tenderId))){
            daoFactory.getTenderDao().updateStatus(tenderId, isActive);
            return;
        }

        throw new NotOwnerException();
    }

    public void deleteTender(String tenderId, String owner) throws NoIdException, DataBaseErrorException {
        if (owner.equals(daoFactory.getTenderDao().getTenderOwner(tenderId))){
            daoFactory.getTenderDao().delete(tenderId);
            return;
        }
    }

    public void createNewTender(String name, String owner, String about)
            throws IllegalArgumentException, DataBaseErrorException {
        name = getValidatedString(name, "Name");
        about = getValidatedString(about, "About");

        Tender tender = new Tender(
                owner,
                about,
                null,
                "",
                name,
                true
        );

        daoFactory.getTenderDao().create(tender);
    }

    public void createNewOffer(String text, String money, String tenderId, String userLogin)
            throws IllegalArgumentException, NoIdException, DataBaseErrorException {
        text = getValidatedString(text, "Text");
        money = getValidatedString(money, "Money");

        try {
            Integer.parseInt(money);
        } catch (Exception e) {
            throw new IllegalArgumentException("Enter number in 'MONEY' field!");
        }

        if (tenderId == null) {
            throw new NoIdException();
        }
        tenderId = tenderId.trim();
        if (tenderId.isEmpty()) {
            throw new NoIdException();
        }

        daoFactory.getTenderOfferDao().create(new TenderOffer(userLogin, text, money, tenderId));
    }
}
