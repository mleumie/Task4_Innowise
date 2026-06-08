package org.laptanovich.webproject.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.laptanovich.webproject.command.Command;
import org.laptanovich.webproject.command.Router;
import org.laptanovich.webproject.entity.Item;
import org.laptanovich.webproject.exception.CommandException;
import org.laptanovich.webproject.exception.ServiceException;
import org.laptanovich.webproject.service.ItemService;
import org.laptanovich.webproject.service.impl.ItemServiceImpl;
import java.math.BigDecimal;

public class EditItemCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Item item = new Item();
        item.setId(Integer.parseInt(request.getParameter("item_id")));
        item.setName(request.getParameter("name"));
        item.setDescription(request.getParameter("description"));
        item.setPrice(new BigDecimal(request.getParameter("price")));
        ItemService itemService = ItemServiceImpl.getInstance();
        try {
            itemService.update(item);
            return new Router("/controller?command=view_items", Router.Type.REDIRECT);
        } catch (ServiceException e) {
            throw new CommandException("Failed to edit item", e);
        }
    }
}
