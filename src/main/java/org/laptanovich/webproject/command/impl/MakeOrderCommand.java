package org.laptanovich.webproject.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.laptanovich.webproject.command.Command;
import org.laptanovich.webproject.command.Router;
import org.laptanovich.webproject.exception.CommandException;
import org.laptanovich.webproject.exception.ServiceException;
import org.laptanovich.webproject.service.OrderService;
import org.laptanovich.webproject.service.impl.OrderServiceImpl;

public class MakeOrderCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user_id");
        int itemId = Integer.parseInt(request.getParameter("item_id"));
        OrderService orderService = OrderServiceImpl.getInstance();
        try {
            orderService.makeOrder(userId, itemId);
            return new Router("/controller?command=view_items", Router.Type.REDIRECT);
        } catch (ServiceException e) {
            throw new CommandException("Failed to make order", e);
        }
    }
}
