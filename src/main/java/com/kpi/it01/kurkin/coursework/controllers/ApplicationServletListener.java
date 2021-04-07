package com.kpi.it01.kurkin.coursework.controllers;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.kpi.it01.kurkin.coursework.controllers.factories.ProcessRequestDecoratorFactory;
import com.kpi.it01.kurkin.coursework.dao.firebase.FirebaseDaoFactory;
import com.kpi.it01.kurkin.coursework.dao.interfaces.DaoFactory;
import com.kpi.it01.kurkin.coursework.services.TenderService;
import com.kpi.it01.kurkin.coursework.services.UserService;
import org.apache.log4j.BasicConfigurator;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.FileInputStream;
import java.io.IOException;

@WebListener
public class ApplicationServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DaoFactory daoFactory = new FirebaseDaoFactory(
                    getFirestoreDB(sce.getServletContext().getRealPath("/WEB-INF/firebasekey.json"))
            );
            ProcessRequestDecoratorFactory factory = new ProcessRequestDecoratorFactory(
                    new UserService(daoFactory),
                    new TenderService(daoFactory)
            );
            sce.getServletContext().setAttribute("factory", factory);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private Firestore getFirestoreDB(String path) throws IOException {
        BasicConfigurator.configure();
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(path)))
                .build();
        FirebaseApp.initializeApp(options);
        return FirestoreClient.getFirestore();
    }
}
