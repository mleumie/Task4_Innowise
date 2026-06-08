package org.laptanovich.webproject.service.impl;

import org.laptanovich.webproject.dao.impl.UserDaoImpl;
import org.laptanovich.webproject.entity.User;
import org.laptanovich.webproject.exception.DaoException;
import org.laptanovich.webproject.exception.ServiceException;
import org.laptanovich.webproject.service.UserService;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance = new UserServiceImpl();
    private final UserDaoImpl userDao = UserDaoImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) throws ServiceException {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            return false;
        }
        try {
            return userDao.authenticate(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean register(String login, String password) throws ServiceException {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            return false;
        }
        try {
            if (userDao.authenticate(login, password)) {
                return false;
            }
            User user = new User();
            user.setLogin(login);
            user.setPasswordHash(PasswordHasher.hash(password));
            user.setRole("USER");
            return userDao.insert(user);
        } catch (DaoException e) {
            throw new ServiceException("Registration failed", e);
        }
    }
}
