package com.kpi.it01.kurkin.coursework.controllers.decorators;

import com.kpi.it01.kurkin.coursework.models.User;
import com.kpi.it01.kurkin.coursework.services.TenderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TenderCreateProcessRequestDecorator extends ProcessRequestDecorator {

    private TenderService tenderService;
    public TenderCreateProcessRequestDecorator(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    @Override
    public void executePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name").trim();
        String about = request.getParameter("about").trim();

        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            forwardToJsp(request, response,"login");
            return;
        }

        try {
            tenderService.createNewTender(name, user.getLogin(), about);
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getLocalizedMessage());
            forwardToJsp(request,  response, "newTender");
            return;
        }
        response.sendRedirect(request.getContextPath()+"/tenders/");
    }

    @Override
    public void executeGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forwardToJsp(request, response, "newTender");
    }
}
