package com.kpi.it01.kurkin.coursework.dao.firebase;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.kpi.it01.kurkin.coursework.dao.interfaces.UserDao;
import com.kpi.it01.kurkin.coursework.exceptions.AlreadyExistsException;
import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.exceptions.NoIdException;
import com.kpi.it01.kurkin.coursework.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserFirebaseDao implements UserDao {

    private final Firestore db;
    public UserFirebaseDao(Firestore db) {
        this.db = db;
    }

    @Override
    public User get(String login) throws NoIdException, DataBaseErrorException {
        try {
            DocumentSnapshot document = db.collection("users").document(login).get().get();

            if (document.exists()) {
                Map<String, Object> data = document.getData();
                return new User(
                        (String) data.get("name"),
                        login,
                        (String) data.get("password")
                );
            }

            throw new NoIdException("You should sign up first!");
        } catch (ExecutionException | InterruptedException e) {
            throw new DataBaseErrorException();
        }
    }

    @Override
    public void create(User user) throws AlreadyExistsException, DataBaseErrorException {
        try {
            DocumentReference docRef = db.collection("users").document(user.getLogin());

            if (docRef.get().get().exists()) {
                throw new AlreadyExistsException("You've already sign up!");
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("name", user.getName());
            data.put("password", user.getPasswordHash());

            docRef.set(data);
        } catch (InterruptedException | ExecutionException e) {
            throw new DataBaseErrorException();
        }
    }
}
