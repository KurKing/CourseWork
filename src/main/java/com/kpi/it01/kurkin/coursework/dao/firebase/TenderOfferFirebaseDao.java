package com.kpi.it01.kurkin.coursework.dao.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.kpi.it01.kurkin.coursework.dao.interfaces.TenderOfferDao;
import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.exceptions.NoIdException;
import com.kpi.it01.kurkin.coursework.models.TenderOffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class TenderOfferFirebaseDao implements TenderOfferDao {

    private final Firestore db;

    public TenderOfferFirebaseDao(Firestore db) {
        this.db = db;
    }

    @Override
    public void create(TenderOffer offer) throws DataBaseErrorException {
        Map<String, Object> data = new HashMap<>();
        data.put("owner", offer.getOwner());
        data.put("text", offer.getText());
        data.put("money", offer.getMoney());

        db.collection("tenders")
                .document(offer.getTenderId())
                .collection("offers")
                .document()
                .set(data);
    }

    @Override
    public List<TenderOffer> getAllFromTenderWithId(String id) throws DataBaseErrorException, NoIdException {
        try {
            ArrayList<TenderOffer> offers = new ArrayList<>();

            List<QueryDocumentSnapshot> offersFromQuery = db.collection("tenders")
                    .document(id)
                    .collection("offers")
                    .orderBy("money")
                    .get().get()
                    .getDocuments();

            for (QueryDocumentSnapshot tenderOfferDocument : offersFromQuery) {
                Map<String, Object> data = tenderOfferDocument.getData();
                offers.add(
                        new TenderOffer(
                                (String) data.get("owner"),
                                (String) data.get("text"),
                                (Long) data.get("money"),
                                id
                        )
                );
            }
            return offers;
        } catch (InterruptedException | ExecutionException e) {
            throw new DataBaseErrorException();
        }
    }
}
