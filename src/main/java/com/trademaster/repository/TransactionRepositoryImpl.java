package com.trademaster.repository;

import com.trademaster.domain.Transaction;
import com.trademaster.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepository {

    @Override
    public void createTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (user_id, stock_id, quantity, date, transaction_type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transaction.getUserId());
            stmt.setInt(2, transaction.getStockId());
            stmt.setInt(3, transaction.getQuantity());
            stmt.setTimestamp(4, transaction.getDate());
            stmt.setString(5, transaction.getTransactionType());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Transaction> getTransactionsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("stock_id"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("date"),
                        rs.getString("transaction_type")
                );
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}
