package org.laptanovich.webproject.service;

import org.laptanovich.webproject.entity.Item;
import org.laptanovich.webproject.exception.ServiceException;
import java.util.List;

public interface ItemService {
    List<Item> findAll() throws ServiceException;

    boolean delete(int itemId) throws ServiceException;

    Item update(Item item) throws ServiceException;
}
