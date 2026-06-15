package org.laptanovich.webproject.service;

import org.laptanovich.webproject.entity.User;
import org.laptanovich.webproject.exception.ServiceException;

import java.util.List;

public interface UserService {
    boolean authenticate(String login, String password) throws ServiceException;

    boolean register(String login, String password) throws ServiceException;

    List<User> findAll() throws ServiceException;

    int getUserIdByLogin(String login) throws ServiceException;
}
