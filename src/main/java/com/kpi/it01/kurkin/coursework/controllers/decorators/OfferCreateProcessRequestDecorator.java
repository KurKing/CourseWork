package com.kpi.it01.kurkin.coursework.controllers.decorators;

import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.exceptions.NoIdException;
import com.kpi.it01.kurkin.coursework.models.User;
import com.kpi.it01.kurkin.coursework.services.TenderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OfferCreateProcessRequestDecorator extends ProcessRequestDecorator {

    private TenderService tenderService;

    public OfferCreateProcessRequestDecorator(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    @Override
    public void executePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            request.setAttribute("message", null);
            forwardToJsp(request, response, "login");
            return;
        }

        try {
            String tenderId = request.getParameter("tenderId");
            tenderService.createNewOffer(
                    request.getParameter("text"),
                    request.getParameter("money"),
                    tenderId,
                    user.getLogin()
            );
            response.sendRedirect(request.getContextPath()+"/tenders/tender?tenderId="+tenderId);
        } catch (IllegalArgumentException e){
            request.setAttribute("errorMessage", e.getLocalizedMessage());
            request.setAttribute("tenderId", request.getParameter("tenderId"));
            forwardToJsp(request,  response, "newOffer");
        } catch (NoIdException | NullPointerException | DataBaseErrorException e) {
            response.sendRedirect(request.getContextPath()+"/tenders/");
        }

    }

    @Override
    public void executeGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("user")!=null){
            request.setAttribute("tenderId", request.getParameter("tenderId"));
            forwardToJsp(request, response, "newOffer");
        } else {
            forwardToJsp(request, response, "login");
        }
    }
}
