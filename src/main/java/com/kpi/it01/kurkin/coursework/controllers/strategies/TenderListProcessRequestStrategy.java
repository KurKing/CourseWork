package com.kpi.it01.kurkin.coursework.controllers.strategies;

import com.kpi.it01.kurkin.coursework.models.User;
import com.kpi.it01.kurkin.coursework.services.TenderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TenderListProcessRequestStrategy extends ProcessRequestStrategy{

    private TenderService tenderService;

    public TenderListProcessRequestStrategy(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    private void tendersList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("tenders", tenderService.getTenders());
        forwardToJsp(request, response, "tendersList");
    }

    private void ownerTenderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User owner = (User) request.getSession().getAttribute("user");

        if (owner != null) {
            request.setAttribute("tenders", tenderService.getTendersWithOwner(owner.getLogin()));
            forwardToJsp(request, response, "tendersList");
        } else {
            forwardToJsp(request, response, "login");
        }
    }

    @Override
    public void executeGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) { pathInfo = "/"; }

        if (pathInfo.equals("/myTenders")) {
            ownerTenderList(request, response);
        } else {
            tendersList(request, response);
        }
    }
}
