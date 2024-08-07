package com.trademaster.repository;

import com.trademaster.domain.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionRepository {
    void createTransaction(Transaction transaction) throws SQLException;
    List<Transaction> getTransactionsByUserId(int userId) throws SQLException;
}
