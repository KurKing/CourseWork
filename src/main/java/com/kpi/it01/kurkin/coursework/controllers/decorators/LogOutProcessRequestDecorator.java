package com.kpi.it01.kurkin.coursework.controllers.decorators;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutProcessRequestDecorator extends ProcessRequestDecorator {
    @Override
    public void executeGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect(request.getContextPath()+"/tenders/");
    }
}
