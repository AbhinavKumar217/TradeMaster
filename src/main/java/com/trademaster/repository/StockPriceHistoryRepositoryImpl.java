package com.trademaster.repository;

import com.trademaster.domain.StockPriceHistory;
import com.trademaster.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockPriceHistoryRepositoryImpl implements StockPriceHistoryRepository {

    @Override
    public void addStockPriceHistory(StockPriceHistory history) throws SQLException {
        String sql = "INSERT INTO stock_price_history (stock_symbol, price, year) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, history.getStockSymbol());
            stmt.setDouble(2, history.getPrice());
            stmt.setInt(3, history.getYear());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<StockPriceHistory> getStockPriceHistory(String stockSymbol, int fromYear, int toYear) throws SQLException {
        String sql = "SELECT * FROM stock_price_history WHERE stock_symbol = ? AND year BETWEEN ? AND ?";
        List<StockPriceHistory> histories = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, stockSymbol);
            stmt.setInt(2, fromYear);
            stmt.setInt(3, toYear);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                StockPriceHistory history = new StockPriceHistory(
                        rs.getInt("id"),
                        rs.getString("stock_symbol"),
                        rs.getDouble("price"),
                        rs.getInt("year")
                );
                histories.add(history);
            }
        }
        return histories;
    }
}
