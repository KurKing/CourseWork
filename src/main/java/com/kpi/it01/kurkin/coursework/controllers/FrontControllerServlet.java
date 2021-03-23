package com.kpi.it01.kurkin.coursework.controllers;

import com.kpi.it01.kurkin.coursework.dal.DataBase;
import com.kpi.it01.kurkin.coursework.exceptions.IncorrectPasswordException;
import com.kpi.it01.kurkin.coursework.exceptions.NotSignUpException;
import com.kpi.it01.kurkin.coursework.models.User;
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
        db = (DataBase) config.getServletContext().getAttribute("dataBase");
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, String jspName)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/"+jspName+".jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }

        switch (pathInfo) {
            case "/login":
                processRequest(request, response,"login");
                break;
            case "/signup":
                processRequest(request, response, "signup");
                break;
            case "/logout":
                request.getSession().removeAttribute("user");
                processRequest(request, response, "tendersList");
                break;
            default:
                processRequest(request, response, "tendersList");
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }

        switch (pathInfo) {
            case "/login":
                try {

                    request.getSession().setAttribute("user",
                            userService.logIn(
                                    request.getParameter("email"),
                                    request.getParameter("password")
                            )
                    );

                } catch (IncorrectPasswordException e) {

                    request.setAttribute("errorMessage", "Incorrect password!");
                    processRequest(request, response,"login");
                    break;

                } catch (NotSignUpException e) {

                    request.setAttribute("errorMessage", "You should sign up first!");
                    processRequest(request, response, "signup");
                    break;

                }

                processRequest(request, response, "tendersList");
                break;

            case "/signup":
                processRequest(request, response, "signup");
                break;
            default:
                processRequest(request, response, "tendersList");
                break;
        }

    }
}
