package org.laptanovich.webproject.service.impl;

import org.laptanovich.webproject.service.UserService;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return login.equals(password); //todo
    }
}
