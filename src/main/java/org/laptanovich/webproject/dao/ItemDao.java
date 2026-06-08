package org.laptanovich.webproject.dao;

import org.laptanovich.webproject.entity.Item;
import org.laptanovich.webproject.exception.DaoException;
import java.util.List;

public interface ItemDao {
    List<Item> findAll() throws DaoException;

    boolean delete(int itemId) throws DaoException;

    Item update(Item item) throws DaoException;
}
