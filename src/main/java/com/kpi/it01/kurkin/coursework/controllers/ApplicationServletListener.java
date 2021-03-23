package com.kpi.it01.kurkin.coursework.controllers;

import com.kpi.it01.kurkin.coursework.dao.DataBase;
import com.kpi.it01.kurkin.coursework.dao.FirebaseDataBase;
import com.kpi.it01.kurkin.coursework.services.UserService;
import org.apache.log4j.BasicConfigurator;

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

        sce.getServletContext().setAttribute("dataBase", db);
        sce.getServletContext().setAttribute("userService", userService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
