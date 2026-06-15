package org.laptanovich.webproject.service;

import org.laptanovich.webproject.entity.Order;
import org.laptanovich.webproject.exception.ServiceException;

import java.util.List;

public interface OrderService {
    boolean makeOrder(int userId, int itemId) throws ServiceException;

    Order update(Order order) throws ServiceException;

    boolean delete(int orderId) throws ServiceException;

    List<Order> findAll() throws ServiceException;
}
