package org.laptanovich.webproject.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.laptanovich.webproject.command.Command;
import org.laptanovich.webproject.command.Router;
import org.laptanovich.webproject.exception.CommandException;
import org.laptanovich.webproject.exception.ServiceException;
import org.laptanovich.webproject.service.ItemService;
import org.laptanovich.webproject.service.impl.ItemServiceImpl;

public class DeleteItemCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        int itemId = Integer.parseInt(request.getParameter("item_id"));
        ItemService itemService = ItemServiceImpl.getInstance();
        try {
            itemService.delete(itemId);
            return new Router("/controller?command=view_items", Router.Type.REDIRECT);
        } catch (ServiceException e) {
            throw new CommandException("Failed to delete item", e);
        }
    }
}
