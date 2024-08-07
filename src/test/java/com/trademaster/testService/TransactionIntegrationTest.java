package com.trademaster.testService;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionIntegrationTest extends BaseIntegrationTest {

    @Test
    void testCreateTransaction() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO users (username, password) VALUES ('janedoe', 'password')");
            statement.executeUpdate("INSERT INTO stocks (symbol, price, quantity) VALUES ('GOOGL', 2800.00, 50)");

            int rowsAffected = statement.executeUpdate(
                    "INSERT INTO transactions (user_id, stock_id, quantity, date, transaction_type) VALUES " +
                            "((SELECT id FROM users WHERE username = 'janedoe'), " +
                            "(SELECT id FROM stocks WHERE symbol = 'GOOGL'), 10, NOW(), 'buy')"
            );
            assertEquals(1, rowsAffected, "Expected one row to be inserted");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM transactions WHERE quantity = 10");
            assertTrue(resultSet.next(), "Transaction should be present in the database");
            assertEquals(10, resultSet.getInt("quantity"));
            assertEquals("buy", resultSet.getString("transaction_type"));
        }
    }

    @Test
    void testDeleteTransaction() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO users (username, password) VALUES ('janedoe', 'password')");
            statement.executeUpdate("INSERT INTO stocks (symbol, price, quantity) VALUES ('GOOGL', 2800.00, 50)");

            statement.executeUpdate(
                    "INSERT INTO transactions (user_id, stock_id, quantity, date, transaction_type) VALUES " +
                            "((SELECT id FROM users WHERE username = 'janedoe'), " +
                            "(SELECT id FROM stocks WHERE symbol = 'GOOGL'), 10, NOW(), 'buy')"
            );
            int rowsAffected = statement.executeUpdate("DELETE FROM transactions WHERE quantity = 10");
            assertEquals(1, rowsAffected, "Expected one row to be deleted");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM transactions WHERE quantity = 10");
            assertFalse(resultSet.next(), "Transaction should not be present in the database");
        }
    }
}
