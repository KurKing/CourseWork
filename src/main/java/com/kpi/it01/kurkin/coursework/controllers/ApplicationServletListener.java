package com.kpi.it01.kurkin.coursework.controllers;

import com.kpi.it01.kurkin.coursework.controllers.factories.ProcessRequestDecoratorFactory;
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

        try {
            DataBase db = new FirebaseDataBase(sce.getServletContext().getRealPath("/WEB-INF/firebasekey.json"));

            ProcessRequestDecoratorFactory factory = new ProcessRequestDecoratorFactory(
                    new UserService(db),
                    new TenderService(db)
            );

            sce.getServletContext().setAttribute("factory", factory);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }
}
