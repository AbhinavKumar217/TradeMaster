package com.trademaster.service;

import com.trademaster.domain.User;
import com.trademaster.repository.UserRepository;
import com.trademaster.repository.UserRepositoryImpl;

import java.sql.SQLException;

public class UserService {
    private final UserRepository userRepository;
    private User loggedInUser;

    public UserService() {
        this.userRepository = new UserRepositoryImpl();
    }

    public synchronized  void registerUser(String username, String password) throws SQLException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.createUser(user);
    }

    public synchronized  boolean login(String username, String password) throws SQLException {
        User user = userRepository.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            loggedInUser = user;
            return true;
        }
        return false;
    }

    public synchronized  void logout() {
        loggedInUser = null;
    }

    public synchronized  User getLoggedInUser() {
        return loggedInUser;
    }
}
