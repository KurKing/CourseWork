package com.kpi.it01.kurkin.coursework.controllers;

import com.kpi.it01.kurkin.coursework.exceptions.*;
import com.kpi.it01.kurkin.coursework.models.TenderOffer;
import com.kpi.it01.kurkin.coursework.models.User;
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
    private void forwardToJsp(HttpServletRequest request, HttpServletResponse response, String jspName)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/"+jspName+".jsp");
        dispatcher.forward(request, response);
    }


    private void newTenderCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name").trim();
        String about = request.getParameter("about").trim();

        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            login(request, response);
            return;
        }

        tenderService.createNewTender(name, user.getLogin(), about);

        tendersList(request, response);
    }
    private void newOfferCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
           forwardToJsp(request, response, "login");
            return;
        }

        try {

            tenderService.createNewOffer(
                    request.getParameter("text"),
                    request.getParameter("money"),
                    request.getParameter("tenderId"),
                    user.getLogin()
            );
            tendersList(request, response);

        } catch (IllegalArgumentException e){

            request.setAttribute("message", e.getLocalizedMessage());
            request.setAttribute("tenderId", request.getParameter("tenderId"));
            forwardToJsp(request,  response, "newOffer");

        } catch (NoIdException | NullPointerException e) {

            tendersList(request, response);

        }

    }
    private void deleteTender(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO
    }

    private void tendersList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("tenders", tenderService.getTenders());
        forwardToJsp(request, response, "tendersList");
    }
    private void ownerTenderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User owner = (User) request.getSession().getAttribute("user");

        if (owner != null) {
            request.setAttribute("tenders", tenderService.getTenders(owner.getLogin()));
            forwardToJsp(request, response, "tendersList");
        } else {
            tendersList(request, response);
        }
    }
    private void tenderWithId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tenderId = request.getParameter("tenderId");
        try {
            request.setAttribute("tender",
                    tenderService.getTenderWithId(tenderId)
            );
            request.setAttribute("user", request.getSession().getAttribute("user"));
            forwardToJsp(request, response, "tender");
        } catch (NoTenderWithIdException | NullPointerException e) {
            tendersList(request,response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            request.getSession().setAttribute("user",
                    userService.logIn(
                            request.getParameter("email").trim(),
                            request.getParameter("password").trim()
                    )
            );

        } catch (IncorrectPasswordException e) {

            request.setAttribute("errorMessage", "Incorrect password!");
            forwardToJsp(request, response,"login");
            return;

        } catch (NotSignUpException e) {

            request.setAttribute("errorMessage", "You should sign up first!");
            forwardToJsp(request, response, "signup");
            return;

        } catch (NoSuchAlgorithmException e) {

            request.getRequestDispatcher("/WEB-INF/undefinedError.html").forward(request, response);
            return;

        }

        tendersList(request, response);
    }
    private void signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            userService.signUp(
                        request.getParameter("email").trim(),
                        request.getParameter("name").trim(),
                        request.getParameter("password").trim(),
                        request.getParameter("password2").trim()
                    );

        } catch (AlreadySignUpException e) {

            request.setAttribute("errorMessage", "You've already sign up!");
            forwardToJsp(request, response, "login");
            return;

        } catch (PasswordMismatchException e) {

            request.setAttribute("errorMessage", "Password mismatch!");
            forwardToJsp(request, response, "signup");
            return;

        } catch (NoSuchAlgorithmException e) {

            request.getRequestDispatcher("/WEB-INF/undefinedError.html").forward(request, response);
            return;

        }

        forwardToJsp(request, response, "login");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }

        switch (pathInfo) {
            case "/login":
                forwardToJsp(request, response,"login");
                break;
            case "/signup":
                forwardToJsp(request, response, "signup");
                break;
            case "/logout":
                request.getSession().removeAttribute("user");
                tendersList(request, response);
                break;
            case "/myTenders":
                ownerTenderList(request, response);
                break;
            case "/newTender":
                forwardToJsp(request, response, "newTender");
                break;
            case "/tender":
                tenderWithId(request, response);
                break;
            case "/disable":
                // TODO
                tenderWithId(request, response);
                break;
            case "/activate":
                // TODO
                tenderWithId(request, response);
                break;
            case "/newOffer":
                if (request.getSession().getAttribute("user")!=null){
                    request.setAttribute("tenderId", request.getParameter("tenderId"));
                    forwardToJsp(request,  response, "newOffer");
                } else {
                    forwardToJsp(request, response,"login");
                }
                break;
            case "/deleteTender":
                // TODO
                tenderWithId(request, response);
                break;
            default:
                tendersList(request, response);
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
            case "/createTender":
                newTenderCreate(request, response);
                break;
            case "/createOffer":
                newOfferCreate(request, response);
                break;
            default:
                tendersList(request, response);
                break;
        }

    }
}
