package com.kpi.it01.kurkin.coursework.controllers;

import com.kpi.it01.kurkin.coursework.dal.DataBase;
import com.kpi.it01.kurkin.coursework.exceptions.AlreadySignUpException;
import com.kpi.it01.kurkin.coursework.exceptions.IncorrectPasswordException;
import com.kpi.it01.kurkin.coursework.exceptions.NotSignUpException;
import com.kpi.it01.kurkin.coursework.exceptions.PasswordMismatchException;
import com.kpi.it01.kurkin.coursework.services.TenderService;
import com.kpi.it01.kurkin.coursework.services.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "FrontControllerServlet", urlPatterns = {"/tenders/*"})
public class FrontControllerServlet extends HttpServlet {

    private UserService userService;
    private TenderService tenderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
        tenderService = (TenderService) config.getServletContext().getAttribute("tenderService");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, String jspName)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/"+jspName+".jsp");
        dispatcher.forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            return;

        } catch (NotSignUpException e) {

            request.setAttribute("errorMessage", "You should sign up first!");
            processRequest(request, response, "signup");
            return;

        } catch (NoSuchAlgorithmException e) {

            request.getRequestDispatcher("/WEB-INF/undefinedError.html").forward(request, response);
            return;

        }

        processRequest(request, response, "tendersList");
    }

    private void signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            userService.signUp(
                        request.getParameter("email"),
                        request.getParameter("name"),
                        request.getParameter("password"),
                        request.getParameter("password2")
                    );

        } catch (AlreadySignUpException e) {

            request.setAttribute("errorMessage", "You've already sign up!");
            processRequest(request, response, "login");
            return;

        } catch (PasswordMismatchException e) {

            request.setAttribute("errorMessage", "Password mismatch!");
            processRequest(request, response, "signup");
            return;

        } catch (NoSuchAlgorithmException e) {

            request.getRequestDispatcher("/WEB-INF/undefinedError.html").forward(request, response);
            return;

        }

        processRequest(request, response, "login");
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
                request.setAttribute("tenders", tenderService.getTenders());
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
                login(request, response);
                break;
            case "/signup":
                signup(request, response);
                break;
            default:
                processRequest(request, response, "tendersList");
                break;
        }

    }
}
