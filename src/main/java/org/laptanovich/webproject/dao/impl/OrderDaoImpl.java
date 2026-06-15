package org.laptanovich.webproject.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.webproject.dao.BaseDao;
import org.laptanovich.webproject.dao.OrderDao;
import org.laptanovich.webproject.dao.UserDao;
import org.laptanovich.webproject.entity.Order;
import org.laptanovich.webproject.exception.DaoException;
import org.laptanovich.webproject.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl extends BaseDao<Order> implements UserDao  {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderDaoImpl instance = new OrderDaoImpl();
    private static final String SQL_SELECT_ALL_ORDERS = "SELECT id, user_id, item_id, status FROM orders";
    private static final String SQL_SELECT_ORDERS_BY_USER = "SELECT id, user_id, item_id, status FROM orders WHERE user_id = ?";
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders (user_id, item_id, status) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET status = ? WHERE id = ?";
    private static final String SQL_DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id = ?";

    private OrderDaoImpl() {}

    public static OrderDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ORDERS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setUserLogin(resultSet.getInt(""));
                order.setItemId(resultSet.getInt("item_id"));
                order.setStatus(resultSet.getString("status"));
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.error("Error finding all orders", e);
            throw new DaoException("Database error during find all orders", e);
        }
        return orders;
    }

    @Override
    public boolean insert(Order order) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ORDER)) {
            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getItemId());
            statement.setString(3, order.getStatus());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error inserting order for user id: {}", order.getUserId(), e);
            throw new DaoException("Database error during order insertion", e);
        }
    }

    @Override
    public Order update(Order order) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER)) {
            statement.setString(1, order.getStatus());
            statement.setLong(2, order.getId());
            if (statement.executeUpdate() > 0) {
                return order;
            }
        } catch (SQLException e) {
            logger.error("Error updating order with id: {}", order.getId(), e);
            throw new DaoException("Database error during order update", e);
        }
    }

    @Override
    public boolean deleteById(long orderId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID)) {
            statement.setLong(1, orderId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting order by id: {}", orderId, e);
            throw new DaoException("Database error during order deletion by id", e);
        }
    }

    @Override
    public List<Order> findOrdersByUserId(long userId) throws DaoException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_USER)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getLong("id"));
                    order.setUserId(resultSet.getLong("user_id"));
                    order.setItemId(resultSet.getLong("item_id"));
                    order.setStatus(resultSet.getString("status"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding orders for user id: {}", userId, e);
            throw new DaoException("Database error during finding orders by user", e);
        }
        return orders;
    }

    @Override
    public boolean delete(Order order) throws DaoException {
        throw new UnsupportedOperationException("Delete by order object is not supported");
    }
}

