package org.laptanovich.webproject.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.laptanovich.webproject.command.Command;
import org.laptanovich.webproject.command.Router;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return new Router("/index.jsp", Router.Type.REDIRECT);
    }
}
