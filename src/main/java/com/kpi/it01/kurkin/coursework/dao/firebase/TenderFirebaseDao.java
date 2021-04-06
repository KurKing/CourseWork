package com.kpi.it01.kurkin.coursework.dao.firebase;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.kpi.it01.kurkin.coursework.dao.interfaces.TenderDao;
import com.kpi.it01.kurkin.coursework.dao.interfaces.TenderOfferDao;
import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.exceptions.NoIdException;
import com.kpi.it01.kurkin.coursework.models.Tender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class TenderFirebaseDao implements TenderDao {

    private final Firestore db;
    private final TenderOfferDao tenderOfferDao;

    public TenderFirebaseDao(Firestore db, TenderOfferDao tenderOfferDao) {
        this.db = db;
        this.tenderOfferDao = tenderOfferDao;
    }

    @Override
    public Tender get(String id) throws ExecutionException, DataBaseErrorException {
        return null;
    }

    @Override
    public String getTenderOwner(String tenderId) throws NoIdException, DataBaseErrorException {
        try {
            DocumentSnapshot document = db.collection("tenders")
                    .document(tenderId).get().get();
            if (document.exists()) {
                return (String) document.getData().get("owner");
            }
            throw new NoIdException();
        } catch (InterruptedException | ExecutionException e) {
            throw new DataBaseErrorException();
        }
    }

    @Override
    public List<Tender> getAll() throws DataBaseErrorException {
        return null;
    }

    @Override
    public List<Tender> getAllWithName(String name) throws DataBaseErrorException {
        return null;
    }

    @Override
    public List<Tender> getAllWithOwner(String owner) throws DataBaseErrorException {
        return null;
    }

    @Override
    public void create(Tender tender) throws DataBaseErrorException {
        Map<String, Object> data = new HashMap<>();
        data.put("owner", tender.getOwner());
        data.put("about", tender.getAbout());
        data.put("name", tender.getName());
        data.put("isActive", true);

        db.collection("tenders")
                .document()
                .set(data);
    }

    @Override
    public void updateStatus(String tenderId, boolean value) throws NoIdException, DataBaseErrorException {
        try {
            DocumentReference docRef = db.collection("tenders").document(tenderId);

            if (!docRef.get().get().exists()) {
                throw new NoIdException();
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("isActive", value);

            docRef.update(data);
        } catch (InterruptedException | ExecutionException e) {
            throw new DataBaseErrorException();
        }
    }

    @Override
    public void delete(String id) throws DataBaseErrorException {
        try {
            List<QueryDocumentSnapshot> offersFromQuery = db.collection("tenders").document(id)
                    .collection("offers")
                    .orderBy("money")
                    .get().get()
                    .getDocuments();

            for (QueryDocumentSnapshot tenderOfferDocument : offersFromQuery) {
                tenderOfferDocument.getReference()
                        .delete();
            }
            db.collection("tenders").document(id)
                    .delete();
        } catch (InterruptedException | ExecutionException e) {
            throw new DataBaseErrorException();
        }
    }
}
