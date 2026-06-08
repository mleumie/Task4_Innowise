package org.laptanovich.webproject.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.laptanovich.webproject.command.Command;
import org.laptanovich.webproject.command.Router;
import org.laptanovich.webproject.entity.Item;
import org.laptanovich.webproject.exception.CommandException;
import org.laptanovich.webproject.exception.ServiceException;
import org.laptanovich.webproject.service.ItemService;
import org.laptanovich.webproject.service.impl.ItemServiceImpl;
import java.util.List;

public class ViewItemsCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ItemService itemService = ItemServiceImpl.getInstance();
        try {
            List<Item> items = itemService.findAll();
            request.setAttribute("items", items);
            return new Router("/WEB-INF/pages/items.jsp", Router.Type.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
