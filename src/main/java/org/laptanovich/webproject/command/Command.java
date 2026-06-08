package org.laptanovich.webproject.command;

import jakarta.servlet.http.HttpServletRequest;
import org.laptanovich.webproject.exception.CommandException;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
