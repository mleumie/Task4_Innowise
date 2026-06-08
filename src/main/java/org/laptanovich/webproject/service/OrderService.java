package org.laptanovich.webproject.service;

import org.laptanovich.webproject.exception.ServiceException;

public interface OrderService {
    boolean makeOrder(String login, int itemId) throws ServiceException;
}
