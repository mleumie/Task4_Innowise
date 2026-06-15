package org.laptanovich.webproject.dao;

import org.laptanovich.webproject.exception.DaoException;

public interface OrderDao {
    boolean deleteById(int orderId) throws DaoException;
}

