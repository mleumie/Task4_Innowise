package org.laptanovich.webproject.service;

import org.laptanovich.webproject.entity.Item;
import org.laptanovich.webproject.exception.DaoException;
import org.laptanovich.webproject.exception.ServiceException;
import java.util.List;

public interface ItemService {
    List<Item> findAll() throws ServiceException;

    boolean delete(int itemId) throws ServiceException;

    boolean insert(Item item) throws ServiceException;

    Item update(Item item) throws ServiceException;
}
