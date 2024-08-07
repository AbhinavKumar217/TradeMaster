package com.trademaster.testService;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class StockIntegrationTest extends BaseIntegrationTest {

    @Test
    void testCreateStock() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate("INSERT INTO stocks (symbol, price, quantity) VALUES ('AAPL', 150.00, 100)");
            assertEquals(1, rowsAffected, "Expected one row to be inserted");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM stocks WHERE symbol = 'AAPL'");
            assertTrue(resultSet.next(), "Stock 'AAPL' should be present in the database");
            assertEquals("AAPL", resultSet.getString("symbol"));
            assertEquals(150.00, resultSet.getDouble("price"));
            assertEquals(100, resultSet.getInt("quantity"));
        }
    }

    @Test
    void testUpdateStockPrice() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO stocks (symbol, price, quantity) VALUES ('AAPL', 150.00, 100)");
            int rowsAffected = statement.executeUpdate("UPDATE stocks SET price = 155.00 WHERE symbol = 'AAPL'");
            assertEquals(1, rowsAffected, "Expected one row to be updated");

            ResultSet resultSet = statement.executeQuery("SELECT price FROM stocks WHERE symbol = 'AAPL'");
            assertTrue(resultSet.next(), "Stock 'AAPL' should be present in the database");
            assertEquals(155.00, resultSet.getDouble("price"));
        }
    }

    @Test
    void testDeleteStock() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO stocks (symbol, price, quantity) VALUES ('AAPL', 150.00, 100)");
            int rowsAffected = statement.executeUpdate("DELETE FROM stocks WHERE symbol = 'AAPL'");
            assertEquals(1, rowsAffected, "Expected one row to be deleted");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM stocks WHERE symbol = 'AAPL'");
            assertFalse(resultSet.next(), "Stock 'AAPL' should not be present in the database");
        }
    }
}
