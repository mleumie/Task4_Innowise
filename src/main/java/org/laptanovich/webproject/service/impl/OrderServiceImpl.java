package org.laptanovich.webproject.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.webproject.dao.impl.ItemDaoImpl;
import org.laptanovich.webproject.dao.impl.OrderDaoImpl;
import org.laptanovich.webproject.dao.impl.UserDaoImpl;
import org.laptanovich.webproject.entity.Item;
import org.laptanovich.webproject.entity.Order;
import org.laptanovich.webproject.entity.User;
import org.laptanovich.webproject.exception.DaoException;
import org.laptanovich.webproject.exception.ServiceException;
import org.laptanovich.webproject.service.OrderService;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderServiceImpl instance = new OrderServiceImpl();
    private final OrderDaoImpl orderDao = OrderDaoImpl.getInstance();

    public OrderServiceImpl() {}

    public static OrderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean makeOrder(int userId, int itemId) throws ServiceException {
        try {
            Optional<User> userOpt = UserDaoImpl.getInstance().findById(userId);
            Optional<Item> itemOpt = ItemDaoImpl.getInstance().findById(itemId);
            if (userOpt.isPresent() && itemOpt.isPresent()) {
                Order order = new Order();
                order.setUser(userOpt.get());
                order.setItem(itemOpt.get());
                order.setStatus("NEW");
                return orderDao.insert(order);
            } else {
                throw new ServiceException("User or Item not found");
            }
        } catch (DaoException e) {
            logger.error("Error creating an order", e);
            throw new ServiceException("Error creating an order", e);
        }
    }

    @Override
    public List<Order> findAll() throws ServiceException {
        try {
            return orderDao.findAll();
        } catch (DaoException e) {
            logger.error("Error finding all orders", e);
            throw new ServiceException("Error finding orders", e);
        }
    }

    @Override
    public boolean delete(int orderId) throws ServiceException {
        try {
            return orderDao.deleteById(orderId);
        } catch (DaoException e) {
            logger.error("Error deleting order", e);
            throw new ServiceException("Error deleting order", e);
        }
    }

    @Override
    public Order update(Order order) throws ServiceException {
        try {
            return orderDao.update(order);
        } catch (DaoException e) {
            logger.error("Error updating order", e);
            throw new ServiceException("Error updating order", e);
        }
    }
}
