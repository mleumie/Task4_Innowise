package org.laptanovich.webproject.service;

import org.laptanovich.webproject.exception.ServiceException;

public interface UserService {
    boolean authenticate(String login, String password) throws ServiceException;

    boolean register(String login, String password) throws ServiceException;
}
