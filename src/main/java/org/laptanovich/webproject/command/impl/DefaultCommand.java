package org.laptanovich.webproject.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.laptanovich.webproject.command.Command;

public class DefaultCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return "index.jsp";
    }
}
