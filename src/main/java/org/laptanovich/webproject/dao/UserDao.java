package org.laptanovich.webproject.dao;

import org.laptanovich.webproject.entity.User;
import org.laptanovich.webproject.exception.DaoException;

public interface UserDao {
    boolean authenticate(String login, String password) throws DaoException;

    boolean insert(User user) throws DaoException;
}
