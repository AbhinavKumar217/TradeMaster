package com.trademaster.repository;

import com.trademaster.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    void createUser(User user) throws SQLException;
    User getUserByUsername(String username) throws SQLException;
    List<User> getAllUsers() throws SQLException;
}
