package org.laptanovich.webproject.command;

import org.laptanovich.webproject.command.impl.*;

public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    SIGN_UP(new SignUpCommand()),
    VIEW_ITEMS(new ViewItemsCommand()),
    MAKE_ORDER(new MakeOrderCommand()),
    DELETE_ITEM(new DeleteItemCommand()),
    EDIT_ITEM(new EditItemCommand()),
    DEFAULT(new DefaultCommand());
    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static Command define(String commandStr) {
        if (commandStr == null || commandStr.isBlank()) {
            return DEFAULT.getCommand();
        }
        try {
            return CommandType.valueOf(commandStr.toUpperCase()).getCommand();
        }
        catch (IllegalArgumentException e) {
            return DEFAULT.getCommand();
        }
    }
}
