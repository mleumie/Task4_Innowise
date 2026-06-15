package org.laptanovich.webproject.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.webproject.dao.impl.UserDaoImpl;
import org.laptanovich.webproject.entity.User;
import org.laptanovich.webproject.exception.DaoException;
import org.laptanovich.webproject.exception.ServiceException;
import org.laptanovich.webproject.service.UserService;
import org.laptanovich.webproject.util.PasswordHasher;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl instance = new UserServiceImpl();
    private final UserDaoImpl userDao = UserDaoImpl.getInstance();

    private UserServiceImpl() {}

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) throws ServiceException {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            return false;
        }
        try {
            Optional<User> userOpt = userDao.findByLogin(login);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                return PasswordHasher.hash(password).equals(user.getPasswordHash());
            }
            return false;
        } catch (DaoException e) {
            logger.error("Error authenticating user", e);
            throw new ServiceException("Error authenticating", e);
        }
    }

    @Override
    public boolean register(String login, String password) throws ServiceException {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            return false;
        }
        try {
            Optional<User> existingUser = userDao.findByLogin(login);
            if (existingUser.isPresent()) {
                return false;
            }
            User user = new User();
            user.setLogin(login);
            user.setPasswordHash(PasswordHasher.hash(password));
            user.setRole("USER");
            return userDao.insert(user);
        } catch (DaoException e) {
            logger.error("Error registering user", e);
            throw new ServiceException("Error registering", e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            logger.error("Error finding all users", e);
            throw new ServiceException("Error finding users", e);
        }
    }

    @Override
    public int getUserIdByLogin(String login) throws ServiceException {
        try {
            return userDao.findByLogin(login)
                    .map(User::getId)
                    .orElseThrow(() -> new ServiceException("User not found"));
        } catch (DaoException e) {
            throw new ServiceException("Error getting user id", e);
        }
    }
}
