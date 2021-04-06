package com.kpi.it01.kurkin.coursework.controllers.decorators;

import com.kpi.it01.kurkin.coursework.services.TenderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TenderWithNameProcessRequestDecorator extends ProcessRequestDecorator {

    private TenderService tenderService;
    public TenderWithNameProcessRequestDecorator(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    @Override
    public void executeGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchString = request.getParameter("search");
        if (searchString != null){
            searchString = searchString.trim();
            if (!searchString.isEmpty()){
                request.setAttribute("tenders", tenderService.getTendersByName(searchString));
                forwardToJsp(request, response, "tendersList");
                return;
            }
        }
        forwardToJsp(request, response, "tenderSearch");
    }
}
