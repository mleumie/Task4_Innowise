package org.laptanovich.webproject.dao;

import org.laptanovich.webproject.entity.Order;
import org.laptanovich.webproject.exception.DaoException;
import java.util.List;

public interface OrderDao {
    List<Order> findByUserLogin(long userId) throws DaoException;
}
