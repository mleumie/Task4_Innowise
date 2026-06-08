package org.laptanovich.webproject.controller;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.laptanovich.webproject.command.Command;
import org.laptanovich.webproject.command.CommandType;
import org.laptanovich.webproject.command.Router;
import org.laptanovich.webproject.exception.CommandException;

@WebServlet(name = "helloServlet", urlPatterns = {"/controller", "*.do"})
public class Controller extends HttpServlet {
    private static final String ERROR_500_PAGE = "/WEB-INF/pages/error/error_500.jsp";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter("command");
        Command command = CommandType.define(commandName);
        try {
            Router router = command.execute(request);
            if (router.getType() == Router.Type.FORWARD) {
                request.getRequestDispatcher(router.getPage()).forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + router.getPage());
            }
        } catch (CommandException e) {
            request.setAttribute("error_message", e.getMessage());
            request.getRequestDispatcher(ERROR_500_PAGE).forward(request, response);
        }
    }
}