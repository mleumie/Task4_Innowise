package org.laptanovich.webproject.dao;

import org.laptanovich.webproject.entity.Item;
import org.laptanovich.webproject.exception.DaoException;

import java.util.Optional;

public interface ItemDao {
    boolean deleteById(int itemId) throws DaoException;

    Optional<Item> findById(long id) throws DaoException;
}
