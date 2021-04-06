package com.kpi.it01.kurkin.coursework.controllers.decorators;

import com.kpi.it01.kurkin.coursework.models.User;
import com.kpi.it01.kurkin.coursework.services.TenderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetStatusProcessRequestDecorator extends ProcessRequestDecorator {

    TenderService tenderService;
    public SetStatusProcessRequestDecorator(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    @Override
    public void executeGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            forwardToJsp(request, response, "login");
            return;
        }

        try {
            String tenderId = request.getParameter("tenderId");
            tenderService.setTenderStatus(
                    request.getParameter("tenderId"),
                    user.getLogin(),
                    request.getParameter("status").equals("1")
            );
            response.sendRedirect(request.getContextPath()+"/tenders/tender?tenderId="+tenderId);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath()+"/tenders/");
        }
    }
}
