package com.trademaster.testService;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class StockPriceHistoryIntegrationTest extends BaseIntegrationTest {

    @Test
    void testRecordStockPriceHistory() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO stocks (symbol, price, quantity) VALUES ('AMZN', 3300.00, 30)");

            int rowsAffected = statement.executeUpdate(
                    "INSERT INTO stock_price_history (stock_id, price, date) VALUES " +
                            "((SELECT id FROM stocks WHERE symbol = 'AMZN'), 3320.00, NOW())"
            );
            assertEquals(1, rowsAffected, "Expected one row to be inserted");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM stock_price_history WHERE price = 3320.00");
            assertTrue(resultSet.next(), "Stock price history should be present in the database");
            assertEquals(3320.00, resultSet.getDouble("price"));
        }
    }

    @Test
    void testDeleteStockPriceHistory() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO stocks (symbol, price, quantity) VALUES ('AMZN', 3300.00, 30)");

            statement.executeUpdate(
                    "INSERT INTO stock_price_history (stock_id, price, date) VALUES " +
                            "((SELECT id FROM stocks WHERE symbol = 'AMZN'), 3320.00, NOW())"
            );
            int rowsAffected = statement.executeUpdate("DELETE FROM stock_price_history WHERE price = 3320.00");
            assertEquals(1, rowsAffected, "Expected one row to be deleted");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM stock_price_history WHERE price = 3320.00");
            assertFalse(resultSet.next(), "Stock price history should not be present in the database");
        }
    }
}
