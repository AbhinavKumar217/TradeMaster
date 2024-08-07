package com.trademaster.testService;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class UserIntegrationTest extends BaseIntegrationTest {

    @Test
    void testCreateUser() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate("INSERT INTO users (username, password) VALUES ('johndoe', 'password123')");
            assertEquals(1, rowsAffected, "Expected one row to be inserted");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE username = 'johndoe'");
            assertTrue(resultSet.next(), "User 'johndoe' should be present in the database");
            assertEquals("johndoe", resultSet.getString("username"));
            assertEquals("password123", resultSet.getString("password"));
        }
    }

    @Test
    void testUpdateUserPassword() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO users (username, password) VALUES ('janedoe', 'oldpassword')");
            int rowsAffected = statement.executeUpdate("UPDATE users SET password = 'newpassword' WHERE username = 'janedoe'");
            assertEquals(1, rowsAffected, "Expected one row to be updated");

            ResultSet resultSet = statement.executeQuery("SELECT password FROM users WHERE username = 'janedoe'");
            assertTrue(resultSet.next(), "User 'janedoe' should be present in the database");
            assertEquals("newpassword", resultSet.getString("password"));
        }
    }

    @Test
    void testDeleteUser() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO users (username, password) VALUES ('johndoe', 'password123')");
            int rowsAffected = statement.executeUpdate("DELETE FROM users WHERE username = 'johndoe'");
            assertEquals(1, rowsAffected, "Expected one row to be deleted");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE username = 'johndoe'");
            assertFalse(resultSet.next(), "User 'johndoe' should not be present in the database");
        }
    }
}
