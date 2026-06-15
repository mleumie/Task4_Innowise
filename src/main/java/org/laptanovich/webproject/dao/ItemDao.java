package org.laptanovich.webproject.dao;

import org.laptanovich.webproject.entity.Item;
import org.laptanovich.webproject.exception.DaoException;
import java.util.List;

public interface ItemDao {
    boolean deleteById(int itemId) throws DaoException;
}
