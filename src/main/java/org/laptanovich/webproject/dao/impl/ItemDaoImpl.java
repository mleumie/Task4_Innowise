package org.laptanovich.webproject.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.webproject.dao.BaseDao;
import org.laptanovich.webproject.dao.ItemDao;
import org.laptanovich.webproject.entity.Item;
import org.laptanovich.webproject.exception.DaoException;
import org.laptanovich.webproject.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl extends BaseDao<Item> implements ItemDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ItemDaoImpl instance = new ItemDaoImpl();
    private static final String SQL_SELECT_ALL_ITEMS = "SELECT id, name, price, description FROM items";
    private static final String SQL_INSERT_ITEM = "INSERT INTO items (name, price, description) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_ITEM = "UPDATE items SET name = ?, price = ?, description = ? WHERE id = ?";
    private static final String SQL_DELETE_ITEM_BY_ID = "DELETE FROM items WHERE id = ?";

    public ItemDaoImpl() {}

    public static ItemDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Item> findAll() throws DaoException {
        List<Item> items = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ITEMS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Item item = new Item();
                item.setId(resultSet.getInt("id"));
                item.setName(resultSet.getString("name"));
                item.setPrice(resultSet.getBigDecimal("price"));
                item.setDescription(resultSet.getString("description"));
                items.add(item);
            }
        } catch (SQLException e) {
            logger.error("Error finding all items", e);
            throw new DaoException("Database error during find all items", e);
        }
        return items;
    }

    @Override
    public boolean deleteById(int itemId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ITEM_BY_ID)) {
            statement.setInt(1, itemId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting item by id: {}", itemId, e);
            throw new DaoException("Database error during item deletion by id", e);
        }
    }

    @Override
    public boolean insert(Item item) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ITEM)) {
            statement.setString(1, item.getName());
            statement.setBigDecimal(2, item.getPrice());
            statement.setString(3, item.getDescription());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error inserting item: {}", item.getName(), e);
            throw new DaoException("Database error during item insertion", e);
        }
    }

    @Override
    public boolean delete(Item item) throws DaoException {
        throw new UnsupportedOperationException("Use deleteById(int) instead of delete(Item)");
    }

    @Override
    public Item update(Item item) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ITEM)) {
            statement.setString(1, item.getName());
            statement.setBigDecimal(2, item.getPrice());
            statement.setString(3, item.getDescription());
            statement.setLong(4, item.getId());
            if (statement.executeUpdate() > 0) {
                return item;
            }
        } catch (SQLException e) {
            logger.error("Error updating item with id: {}", item.getId(), e);
            throw new DaoException("Database error during item update", e);
        }
        return null;
    }
}
