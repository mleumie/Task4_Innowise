package org.laptanovich.webproject.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.webproject.dao.BaseDao;
import org.laptanovich.webproject.dao.OrderDao;
import org.laptanovich.webproject.entity.Item;
import org.laptanovich.webproject.entity.Order;
import org.laptanovich.webproject.entity.User;
import org.laptanovich.webproject.exception.DaoException;
import org.laptanovich.webproject.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl extends BaseDao<Order> implements OrderDao {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderDaoImpl instance = new OrderDaoImpl();
    private static final String SQL_FIND_ALL = "SELECT id, user_id, item_id, status FROM orders";
    private static final String SQL_CREATE = "INSERT INTO orders (user_id, item_id, status) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE orders SET user_id = ?, item_id = ?, status = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM orders WHERE id = ?";

    private OrderDaoImpl() {}

    public static OrderDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setStatus(resultSet.getString("status"));
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                order.setUser(user);
                Item item = new Item();
                item.setId(resultSet.getInt("item_id"));
                order.setItem(item);
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.error("Failed to find all orders", e);
            throw new DaoException("Failed to find all orders", e);
            }
        return orders;
    }

    @Override
    public boolean insert(Order entity) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE)) {
            statement.setInt(1, entity.getUser().getId());
            statement.setInt(2, entity.getItem().getId());
            statement.setString(3, entity.getStatus());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to create order", e);
            throw new DaoException("Failed to create order", e);
        }
    }

    @Override
    public Order update(Order entity) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setInt(1, entity.getUser().getId());
            statement.setInt(2, entity.getItem().getId());
            statement.setString(3, entity.getStatus());
            statement.setInt(4, entity.getId());
            if (statement.executeUpdate() == 0) {
                throw new DaoException("Update failed, order not found");
            }
            return entity;
        } catch (SQLException e) {
            logger.error("Failed to update order", e);
            throw new DaoException("Failed to update order", e);
        }
    }

    @Override
    public boolean deleteById(int orderId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setInt(1, orderId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to delete order by id: {}", orderId, e);
            throw new DaoException("Failed to delete order", e);
        }
    }

    @Override
    public boolean delete(Order order) throws DaoException {
        throw new UnsupportedOperationException("Delete by entity is not supported");
    }
}