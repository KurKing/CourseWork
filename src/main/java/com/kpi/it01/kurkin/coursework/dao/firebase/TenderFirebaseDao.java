package com.kpi.it01.kurkin.coursework.dao.firebase;

import com.google.cloud.firestore.*;
import com.kpi.it01.kurkin.coursework.dao.interfaces.TenderDao;
import com.kpi.it01.kurkin.coursework.dao.interfaces.TenderOfferDao;
import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.exceptions.NoIdException;
import com.kpi.it01.kurkin.coursework.models.Tender;
import com.kpi.it01.kurkin.coursework.models.TenderOffer;

import java.util.ArrayList;
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
    public Tender get(String id) throws DataBaseErrorException, NoIdException {
        try {
            Map<String, Object> data = db.collection("tenders")
                    .document(id).get().get().getData();

            if (data == null) { throw new NoIdException(); }

            return new Tender(
                    (String) data.get("owner"),
                    (String) data.get("about"),
                    tenderOfferDao.getAllFromTenderWithId(id),
                    id,
                    (String) data.get("name"),
                    (boolean) data.get("isActive")
            );
        } catch (InterruptedException | ExecutionException e) {
            throw new DataBaseErrorException();
        }
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
        return fetchTenders(
                db.collection("tenders")
                        .whereEqualTo("isActive", true)
        );
    }

    @Override
    public List<Tender> getAllWithName(String name) throws DataBaseErrorException {
        return fetchTenders(
                db.collection("tenders")
                        .whereEqualTo("isActive", true)
                        .whereEqualTo("name", name)
        );
    }

    @Override
    public List<Tender> getAllWithOwner(String owner) throws DataBaseErrorException {
        return fetchTenders(
                db.collection("tenders")
                        .whereEqualTo("owner", owner)
        );
    }

    private List<Tender> fetchTenders(Query query) throws DataBaseErrorException {
        try {
            List<Tender> tenders = new ArrayList<>();
            List<QueryDocumentSnapshot> tendersFromQuery = query
                    .get().get()
                    .getDocuments();
            for (QueryDocumentSnapshot tender : tendersFromQuery) {
                List<TenderOffer> offers = tenderOfferDao.getAllFromTenderWithId(tender.getId());
                Map<String, Object> data = tender.getData();
                tenders.add(
                        new Tender(
                                (String) data.get("owner"),
                                (String) data.get("about"),
                                offers,
                                tender.getId(),
                                (String) data.get("name"),
                                (boolean) data.get("isActive")
                        )
                );
            }
            return tenders;
        } catch (InterruptedException | NoIdException | ExecutionException e) {
            throw new DataBaseErrorException();
        }
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
