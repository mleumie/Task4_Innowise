package org.laptanovich.webproject.service.impl;

import org.laptanovich.webproject.dao.ItemDao;
import org.laptanovich.webproject.entity.Item;
import org.laptanovich.webproject.exception.DaoException;
import org.laptanovich.webproject.exception.ServiceException;
import org.laptanovich.webproject.service.ItemService;
import java.math.BigDecimal;
import java.util.List;

public class ItemServiceImpl implements ItemService {
    private static ItemServiceImpl instance;
    private final ItemDao itemDao = ItemDaoImpl.getInstance();

    private ItemServiceImpl() {
    }

    public static ItemServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Item> findAll() throws ServiceException {
        try {
            return itemDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to load items", e);
        }
    }

    @Override
    public boolean delete(int itemId) throws ServiceException {
        try {
            return itemDao.delete(itemId);
        } catch (DaoException e) {
            throw new ServiceException("Cannot delete item", e);
        }
    }

    @Override
    public Item update(Item item) throws ServiceException {
        if (item.getName() == null || item.getName().isBlank()) {
            throw new ServiceException("Item name is empty");
        }
        if (item.getPrice() == null || item.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException("Item price is invalid");
        }
        try {
            return itemDao.update(item);
        } catch (DaoException e) {
            throw new ServiceException("Cannot update item", e);
        }
    }
}
