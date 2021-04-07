package com.kpi.it01.kurkin.coursework.controllers.decorators;

import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.models.User;
import com.kpi.it01.kurkin.coursework.services.TenderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TenderListProcessRequestDecorator extends ProcessRequestDecorator {

    private TenderService tenderService;
    public TenderListProcessRequestDecorator(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    private void tendersList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DataBaseErrorException {
        request.setAttribute("tenders", tenderService.getTenders());
        forwardToJsp(request, response, "tendersList");
    }

    private void ownerTenderList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DataBaseErrorException {
        User owner = (User) request.getSession().getAttribute("user");
        if (owner != null) {
            request.setAttribute("tenders", tenderService.getTendersWithOwner(owner.getLogin()));
            forwardToJsp(request, response, "tendersList");
        } else {
            forwardToJsp(request, response, "login");
        }
    }

    @Override
    public void executeGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if ("/myTenders".equals(request.getPathInfo())) {
                ownerTenderList(request, response);
            } else {
                tendersList(request, response);
            }
        } catch (DataBaseErrorException e) {
            request.getRequestDispatcher("/WEB-INF/undefinedError.html").forward(request, response);
            return;
        }
    }
}
