package com.kpi.it01.kurkin.coursework.controllers.decorators;

import com.kpi.it01.kurkin.coursework.exceptions.AlreadyExistsException;
import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.exceptions.PasswordMismatchException;
import com.kpi.it01.kurkin.coursework.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class SignUpProcessRequestDecorator extends ProcessRequestDecorator {

    private UserService userService;
    public SignUpProcessRequestDecorator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void executePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            userService.signUp(
                    request.getParameter("email"),
                    request.getParameter("name"),
                    request.getParameter("password"),
                    request.getParameter("password2")
            );
        }  catch (PasswordMismatchException | NullPointerException | IllegalArgumentException | AlreadyExistsException e) {
            request.setAttribute("errorMessage", e.getLocalizedMessage());
            forwardToJsp(request, response, "signup");
            return;
        } catch (NoSuchAlgorithmException | DataBaseErrorException e) {
            request.getRequestDispatcher("/WEB-INF/undefinedError.html").forward(request, response);
            return;
        }
        forwardToJsp(request, response,"login");
    }

    @Override
    public void executeGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forwardToJsp(request, response, "signup.jsp");
    }
}
