package org.laptanovich.webproject.dao.impl;

import org.laptanovich.webproject.dao.ItemDao;
import org.laptanovich.webproject.entity.Item;
import org.laptanovich.webproject.exception.DaoException;

import java.util.List;

public class ItemDaoImpl implements ItemDao {
    private static final ItemDaoImpl instance = new ItemDaoImpl();
    private static final String SQL_FIND_ALL = "SELECT * FROM items";
    private static final String SQL_DELETE = "DELETE FROM items WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE items SET name = ?, price = ? WHERE id = ?";

    public ItemDaoImpl() {
    }

    public static ItemDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Item> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean delete(int itemId) throws DaoException {
        return false;
    }

    @Override
    public Item update(Item item) throws DaoException {
        return null;
    }
}
