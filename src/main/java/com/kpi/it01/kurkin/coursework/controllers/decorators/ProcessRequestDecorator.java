package com.kpi.it01.kurkin.coursework.controllers.decorators;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class ProcessRequestDecorator {

    protected void forwardToJsp(HttpServletRequest request, HttpServletResponse response, String jspName)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/"+jspName+".jsp");
        dispatcher.forward(request, response);
    }

    public void executePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+"/tenders/");
    }

    public abstract void executeGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

}
