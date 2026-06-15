package org.laptanovich.webproject.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.webproject.dao.BaseDao;
import org.laptanovich.webproject.dao.UserDao;
import org.laptanovich.webproject.entity.User;
import org.laptanovich.webproject.exception.DaoException;
import org.laptanovich.webproject.pool.ConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    private static final UserDaoImpl instance = new UserDaoImpl();
    private static final String SQL_AUTHENTICATE = "SELECT id, login, role FROM users WHERE login = ? AND password_hash = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO users (login, password_hash, role) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_ALL_USERS = "SELECT id, login, role FROM users";
    private static final String SQL_FIND_BY_LOGIN = "SELECT id, login, password_hash, role FROM users WHERE login = ?";

    private UserDaoImpl() {}

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_AUTHENTICATE)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.error("Error during authentication for user: {}", login, e);
            throw new DaoException("Database error during user authentication", e);
        }
    }

    @Override
    public boolean insert(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getRole());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Error inserting user: {}", user.getLogin(), e);
            throw new DaoException("Database error during user insertion", e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setRole(resultSet.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Error finding all users", e);
            throw new DaoException("Database error during find all users", e);
        }
        return users;
    }

    @Override
    public Optional<User> findById(long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, login, role FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setLogin(resultSet.getString("login"));
                    user.setRole(resultSet.getString("role"));
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding user by id", e);
            throw new DaoException("Database error finding user by id", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setLogin(resultSet.getString("login"));
                    user.setPasswordHash(resultSet.getString("password_hash"));
                    user.setRole(resultSet.getString("role"));
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding user by login: {}", login, e);
            throw new DaoException("Database error finding user by login", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(User user) throws DaoException {
        throw new UnsupportedOperationException("Delete operation not supported for User");
    }

    @Override
    public User update(User user) throws DaoException {
        throw new UnsupportedOperationException("Update operation not supported for User");
    }
}
