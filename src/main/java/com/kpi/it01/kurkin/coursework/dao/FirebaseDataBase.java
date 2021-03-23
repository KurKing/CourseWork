package com.kpi.it01.kurkin.coursework.dao;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseDataBase implements DataBase {

    private final Firestore db;

    public FirebaseDataBase() throws IOException {
        FileInputStream serviceAccount =
            new FileInputStream("firebasekey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

        FirebaseApp.initializeApp(options);

        db = FirestoreClient.getFirestore();
    }

}
