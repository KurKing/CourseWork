package com.kpi.it01.kurkin.coursework.controllers;

import com.kpi.it01.kurkin.coursework.controllers.factories.ProcessRequestDecoratorFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "FrontControllerServlet", urlPatterns = {"/tenders/*"})
public class FrontControllerServlet extends HttpServlet {

    private ProcessRequestDecoratorFactory factory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        factory = (ProcessRequestDecoratorFactory) config.getServletContext().getAttribute("factory");
    }

    private String getPathFromRequest(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) { pathInfo = "/"; }
        return pathInfo;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.getDecoratorForPath(getPathFromRequest(request))
                .executeGet(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.getDecoratorForPath(getPathFromRequest(request))
                .executePost(request, response);

    }
}
