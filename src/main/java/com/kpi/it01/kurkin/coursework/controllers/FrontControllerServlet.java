package com.kpi.it01.kurkin.coursework.controllers;

import com.kpi.it01.kurkin.coursework.dao.DataBase;
import com.kpi.it01.kurkin.coursework.dao.FirebaseDataBase;
import com.kpi.it01.kurkin.coursework.services.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "FrontControllerServlet", urlPatterns = {"/tenders/*"})
public class FrontControllerServlet extends HttpServlet {

    private DataBase db;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            db = new FirebaseDataBase();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }

        userService = new UserService(db);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, String jspName)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/"+jspName+".jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        switch (request.getPathInfo()) {
            case "/login":
                userService.logIn(
                        request.getParameter("email"),
                        request.getParameter("password")
                );
                    processRequest(request, response,"tenders");
                    break;

//                request.setAttribute("message", "Incorrect password or email!");
//                processRequest(request, response,"login");
//
//                break;
            case "/signup":
                processRequest(request, response, "signup");
                break;
            default:
                processRequest(request, response, "tenders");
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getPathInfo()) {
            case "/login":
                processRequest(request, response, "tenders");
                break;
            case "/signup":
                processRequest(request, response, "tenders");
                break;
            default:
                processRequest(request, response, "tenders");
                break;
        }

    }
}
