package org.laptanovich.webproject.command;

import org.laptanovich.webproject.command.impl.AddUserCommand;
import org.laptanovich.webproject.command.impl.DefaultCommand;
import org.laptanovich.webproject.command.impl.LoginCommand;
import org.laptanovich.webproject.command.impl.LogoutCommand;

public enum CommandType {
    ADD_USER(new AddUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT(new DefaultCommand());
    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandStr) {
        CommandType current = CommandType.valueOf(commandStr.toUpperCase());
        return current.command;
    }
}
