package com.kpi.it01.kurkin.coursework.controllers.decorators;

import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.exceptions.NoIdException;
import com.kpi.it01.kurkin.coursework.services.TenderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TenderWithIdProcessRequestDecorator extends ProcessRequestDecorator {

    private TenderService tenderService;
    public TenderWithIdProcessRequestDecorator(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    @Override
    public void executeGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("tender",
                    tenderService.getTenderWithId(request.getParameter("tenderId"))
            );
            request.setAttribute("user", request.getSession().getAttribute("user"));
            forwardToJsp(request, response, "tender");
        } catch (NoIdException | NullPointerException e) {
            response.sendRedirect(request.getContextPath()+"/tenders/");
        } catch (DataBaseErrorException e) {
            request.getRequestDispatcher("/WEB-INF/undefinedError.html").forward(request, response);
            return;
        }
    }
}
