package org.laptanovich.webproject.service.impl;

import org.laptanovich.webproject.dao.OrderDao;
import org.laptanovich.webproject.exception.DaoException;
import org.laptanovich.webproject.exception.ServiceException;
import org.laptanovich.webproject.service.OrderService;

public class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl instance;
    private final OrderDao orderDao = OrderDaoImpl.getInstance();

    public OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean makeOrder(String login, int itemId) throws ServiceException {
        try {
            return orderDao.makeOrder(login, itemId);
        } catch (DaoException e) {
            throw new ServiceExceptio("Error making order", e);
        }
    }
}
