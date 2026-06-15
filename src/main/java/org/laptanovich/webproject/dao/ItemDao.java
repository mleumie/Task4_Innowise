package org.laptanovich.webproject.dao;

import org.laptanovich.webproject.exception.DaoException;

public interface ItemDao {
    boolean deleteById(int itemId) throws DaoException;
}
