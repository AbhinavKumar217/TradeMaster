package com.trademaster.repository;

import com.trademaster.domain.Stock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.trademaster.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockRepositoryImpl implements StockRepository {

	@Override
    public void createOrUpdateStock(String symbol, double price, int quantity) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {

            String checkQuery = "SELECT id FROM stocks WHERE symbol = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, symbol);
                try (ResultSet resultSet = checkStatement.executeQuery()) {

                    if (resultSet.next()) {
                        String updateQuery = "UPDATE stocks SET price = ?, quantity = ? WHERE symbol = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setDouble(1, price);
                            updateStatement.setInt(2, quantity);
                            updateStatement.setString(3, symbol);
                            updateStatement.executeUpdate();
                        }
                    } else {
                        String insertQuery = "INSERT INTO stocks (symbol, price, quantity) VALUES (?, ?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            insertStatement.setString(1, symbol);
                            insertStatement.setDouble(2, price);
                            insertStatement.setInt(3, quantity);
                            insertStatement.executeUpdate();
                        }
                    }
                }
            }
        }
    }

    @Override
    public Stock getStockBySymbol(String symbol) throws SQLException {
        String sql = "SELECT * FROM stocks WHERE symbol = ?";
        Stock stock = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, symbol);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                stock = new Stock(
                        rs.getInt("id"),
                        rs.getString("symbol"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
            }
        }
        return stock;
    }

    @Override
    public synchronized List<Stock> getAllStocks() throws SQLException {
        String sql = "SELECT * FROM stocks";
        List<Stock> stocks = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Stock stock = new Stock(
                        rs.getInt("id"),
                        rs.getString("symbol"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                stocks.add(stock);
            }
        }
        return stocks;
    }
}
