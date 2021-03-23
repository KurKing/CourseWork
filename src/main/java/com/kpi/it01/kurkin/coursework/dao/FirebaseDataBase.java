package com.kpi.it01.kurkin.coursework.dao;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.kpi.it01.kurkin.coursework.exceptions.NotSignUpException;
import com.kpi.it01.kurkin.coursework.models.User;
import org.apache.log4j.BasicConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirebaseDataBase implements DataBase {

    private Firestore db;

    public FirebaseDataBase(String path) throws IOException {

        Thread newThread = new Thread(() -> {
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


        });
        newThread.start();

    }

    @Override
    public User getUserByLogin(String login) throws NotSignUpException {
        if (db == null) {return null;}
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

}
