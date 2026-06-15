package org.laptanovich.webproject.dao;

import org.laptanovich.webproject.entity.User;
import org.laptanovich.webproject.exception.DaoException;
import java.util.Optional;

public interface UserDao {
    boolean authenticate(String login, String password) throws DaoException;

    Optional<User> findById(long id) throws DaoException;

    Optional<User> findByLogin(String login) throws DaoException;
}
