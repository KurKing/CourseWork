package com.kpi.it01.kurkin.coursework.dal;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.kpi.it01.kurkin.coursework.exceptions.AlreadySignUpException;
import com.kpi.it01.kurkin.coursework.exceptions.NoTenderWithIdException;
import com.kpi.it01.kurkin.coursework.exceptions.NotSignUpException;
import com.kpi.it01.kurkin.coursework.models.Tender;
import com.kpi.it01.kurkin.coursework.models.TenderOffer;
import com.kpi.it01.kurkin.coursework.models.User;
import org.apache.log4j.BasicConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirebaseDataBase implements DataBase {

    private Firestore db;

    public FirebaseDataBase(String path) throws IOException {

            BasicConfigurator.configure();

            try {

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(new FileInputStream(path)))
                        .build();
                FirebaseApp.initializeApp(options);
                db = FirestoreClient.getFirestore();

            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }

    }

    @Override
    public void setUser(User user) throws AlreadySignUpException {

        DocumentReference docRef = db.collection("users").document(user.getLogin());

        try {
            if (docRef.get().get().exists()) {
                throw new AlreadySignUpException();
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", user.getName());
        data.put("password", user.getPasswordHash());

        docRef.set(data);
    }

    @Override
    public void setTenderData(String tenderId, String name, Object value) {
        DocumentReference docRef = db.collection("tenders").document(tenderId);

        HashMap<String, Object> data = new HashMap<>();
        data.put(name, value);

        docRef.set(data);
    }

    @Override
    public User getUserByLogin(String login) throws NotSignUpException {

        DocumentReference docRef = db.collection("users").document(login);
        DocumentSnapshot document;

        try {
            document = docRef.get().get();
        } catch (InterruptedException | ExecutionException e) {
            e.getLocalizedMessage();
            return null;
        }

        if (document.exists()) {
            Map<String, Object> data = document.getData();
            return new User(
                    (String) data.get("name"),
                    login,
                    (String) data.get("password")
            );
        }

        throw new NotSignUpException();
    }

    @Override
    public ArrayList<Tender> getTenders(String owner) throws ExecutionException, InterruptedException {
        return fetchTenders(
                db.collection("tenders")
                .whereEqualTo("owner", owner)
        );
    }

    @Override
    public ArrayList<Tender> getTenders() throws ExecutionException, InterruptedException {
        return fetchTenders(
                db.collection("tenders")
                .whereEqualTo("isActive", true)
        );
    }

    @Override
    public Tender getTenderWithId(String id) throws NoTenderWithIdException, ExecutionException, InterruptedException {
            Map<String, Object> data = db.collection("tenders")
                    .document(id).get().get().getData();

            if (data == null) { throw new NoTenderWithIdException(); }

            return new Tender(
                    (String) data.get("owner"),
                    (String) data.get("about"),
                    fetchTenderOffersFromDocument(
                            db.collection("tenders")
                            .document(id)
                    ),
                    id,
                    (String) data.get("name"),
                    (boolean) data.get("isActive")
            );
    }

    private  ArrayList<Tender> fetchTenders(Query query) throws ExecutionException, InterruptedException {
        ArrayList<Tender> tenders = new ArrayList<>();

        List<QueryDocumentSnapshot> tendersFromQuery = query
                .get().get()
                .getDocuments();

        for (QueryDocumentSnapshot tender : tendersFromQuery) {

            ArrayList<TenderOffer> offers = fetchTenderOffersFromDocument(tender.getReference());

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
    }

    private ArrayList<TenderOffer> fetchTenderOffersFromDocument(DocumentReference document) throws ExecutionException, InterruptedException {
        ArrayList<TenderOffer> offers = new ArrayList<>();

        List<QueryDocumentSnapshot> offersFromQuery = document
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
                            (Long) data.get("money")
                    )
            );

        }

        return offers;
    }

    @Override
    public void createTender(Tender newTender) {
        Map<String, Object> data = new HashMap<>();
        data.put("owner", newTender.getOwner());
        data.put("about", newTender.getAbout());
        data.put("name", newTender.getName());
        data.put("isActive", true);

        db.collection("tenders")
                .document()
                .set(data);
    }

    @Override
    public void deleteTender(String tenderId) {
        // TODO
    }

}
