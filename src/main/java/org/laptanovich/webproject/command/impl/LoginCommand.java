package org.laptanovich.webproject.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.laptanovich.webproject.command.Command;
import org.laptanovich.webproject.command.Router;
import org.laptanovich.webproject.exception.CommandException;
import org.laptanovich.webproject.exception.ServiceException;
import org.laptanovich.webproject.service.UserService;
import org.laptanovich.webproject.service.impl.UserServiceImpl;

public class LoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UserService userService = UserServiceImpl.getInstance();
        try {
            if (userService.authenticate(login, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("user_name", login);
                int userId = userService.getUserIdByLogin(login);
                session.setAttribute("user_id", userId);
                return new Router("/controller?command=view_items", Router.Type.REDIRECT);
            }
            request.setAttribute("errorMessage", "incorrect login or pass");
            return new Router("/WEB-INF/pages/login.jsp", Router.Type.FORWARD);
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", e.getMessage());
            return new Router("/WEB-INF/pages/login.jsp", Router.Type.FORWARD);
        }
    }
}
