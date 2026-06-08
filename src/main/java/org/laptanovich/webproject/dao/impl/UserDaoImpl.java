package org.laptanovich.webproject.dao.impl;

import org.laptanovich.webproject.dao.BaseDao;
import org.laptanovich.webproject.dao.UserDao;
import org.laptanovich.webproject.entity.User;
import org.laptanovich.webproject.exception.DaoException;
import org.laptanovich.webproject.pool.ConnectionPool;
import java.sql.*;
import java.util.List;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private static UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {}

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean authenticate(String login, String password) throws DaoException {


        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            //@Language("SQL")
            //String sql = "SELECT idphonebook, lastname, phone FROM phonebook";
            //ResultSet resultSet = statement.executeQuery(sql);
            //while (resultSet.next()) {

            //    return false;
            //}
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return false;
    }
}
