package org.laptanovich.webproject.dao.impl;

import org.laptanovich.webproject.dao.BaseDao;
import org.laptanovich.webproject.dao.UserDao;
import org.laptanovich.webproject.entity.User;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
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
    public boolean authenticate(String login, String password) {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String url = "jdbc:postgresql://localhost:5432/testphones";
        Properties prop = new Properties();
        prop.put("user", "postgres");
        prop.put("password", "1303");

        try (Connection connection = DriverManager.getConnection(url, prop);
             Statement statement = connection.createStatement()) {
            String sql = "SELECT idphonebook, lastname, phone FROM phonebook";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
