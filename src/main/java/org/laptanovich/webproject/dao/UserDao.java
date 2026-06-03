package org.laptanovich.webproject.dao;

public interface UserDao {
    boolean authenticate(String login, String password);
}
