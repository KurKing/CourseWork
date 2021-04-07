package com.kpi.it01.kurkin.coursework.controllers.decorators;

import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.exceptions.NoIdException;
import com.kpi.it01.kurkin.coursework.models.User;
import com.kpi.it01.kurkin.coursework.services.TenderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProcessRequestDecorator extends ProcessRequestDecorator {

    private TenderService tenderService;
    public DeleteProcessRequestDecorator(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    @Override
    public void executeGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                forwardToJsp(request, response, "login");
                return;
            }
            String tenderId = request.getParameter("tenderId");
            if (tenderId.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/tenders/");
                return;
            }
            tenderService.deleteTender(tenderId, user.getLogin());
            response.sendRedirect(request.getContextPath() + "/tenders/");
        } catch (NoIdException | DataBaseErrorException e) {
            request.getRequestDispatcher("/WEB-INF/undefinedError.html").forward(request, response);
            return;
        }
    }
}
