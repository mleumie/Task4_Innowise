package org.laptanovich.webproject.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.webproject.dao.impl.ItemDaoImpl;
import org.laptanovich.webproject.entity.Item;
import org.laptanovich.webproject.exception.DaoException;
import org.laptanovich.webproject.exception.ServiceException;
import org.laptanovich.webproject.service.ItemService;
import java.math.BigDecimal;
import java.util.List;

public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LogManager.getLogger();
    private static final ItemServiceImpl instance = new ItemServiceImpl();
    private final ItemDaoImpl itemDao = ItemDaoImpl.getInstance();

    private ItemServiceImpl() {}
    
    public static ItemServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Item> findAll() throws ServiceException {
        try {
            return itemDao.findAll();
        } catch (DaoException e) {
            logger.error("Error finding all items", e);
            throw new ServiceException("Error finding all items", e);
        }
    }

    @Override
    public boolean delete(int itemId) throws ServiceException {
        try {
            return itemDao.deleteById(itemId);
        } catch (DaoException e) {
            logger.error("Error deleting item", e);
            throw new ServiceException("Error deleting item", e);
        }
    }

    @Override
    public boolean insert(Item item) throws ServiceException {
        try {
            return itemDao.insert(item);
        } catch (DaoException e) {
            logger.error("Error inserting item", e);
            throw new ServiceException("Error inserting item", e);
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
            logger.error("Error updating item", e);
            throw new ServiceException("Error updating item", e);
        }
    }
}
