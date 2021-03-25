package com.kpi.it01.kurkin.coursework.controllers;

import com.kpi.it01.kurkin.coursework.dal.DataBase;
import com.kpi.it01.kurkin.coursework.dal.FirebaseDataBase;
import com.kpi.it01.kurkin.coursework.services.TenderService;
import com.kpi.it01.kurkin.coursework.services.UserService;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebListener
public class ApplicationServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        DataBase db = null;
        try {
            db = new FirebaseDataBase(sce.getServletContext().getRealPath("/WEB-INF/firebasekey.json"));
        } catch (IOException e) {
            System.out.println(e);
        }

        UserService userService = new UserService(db);
        TenderService tenderService = new TenderService(db);

        sce.getServletContext().setAttribute("userService", userService);
        sce.getServletContext().setAttribute("tenderService", tenderService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
