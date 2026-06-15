package org.laptanovich.webproject.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.laptanovich.webproject.command.Command;
import org.laptanovich.webproject.command.Router;
import org.laptanovich.webproject.exception.CommandException;
import org.laptanovich.webproject.exception.ServiceException;
import org.laptanovich.webproject.service.UserService;
import org.laptanovich.webproject.service.impl.UserServiceImpl;

public class SignUpCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UserService userService = UserServiceImpl.getInstance();
        try {
            boolean registered = userService.register(login, password);
            if (registered) {
                request.setAttribute("errorMessage", "Registration successful! Please log in.");
                return new Router("/WEB-INF/pages/login.jsp", Router.Type.FORWARD);
            } else {
                request.setAttribute("errorMessage", "User already exists");
                return new Router("/WEB-INF/pages/signup.jsp", Router.Type.FORWARD);
            }
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            return new Router("/WEB-INF/pages/signup.jsp", Router.Type.FORWARD);
        }
    }
}