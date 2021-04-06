package com.kpi.it01.kurkin.coursework.dao.interfaces;

import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.exceptions.NoIdException;
import com.kpi.it01.kurkin.coursework.models.Tender;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface TenderDao {
    Tender get(String id) throws ExecutionException, DataBaseErrorException;
    String getTenderOwner(String tenderId) throws NoIdException, DataBaseErrorException;

    List<Tender> getAll() throws DataBaseErrorException;
    List<Tender> getAllWithName(String name) throws DataBaseErrorException;
    List<Tender> getAllWithOwner(String owner) throws DataBaseErrorException;

    void create(Tender tender) throws DataBaseErrorException;
    void updateStatus(String tenderId, boolean value) throws NoIdException, DataBaseErrorException;

    void delete(String id) throws DataBaseErrorException;
}
