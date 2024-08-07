package com.trademaster.domain;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int userId;
    private int stockId;
    private int quantity;
    private Timestamp date;
    private String transactionType;

    public Transaction() {}

    public Transaction(int id, int userId, int stockId, int quantity, Timestamp date, String transactionType) {
        this.id = id;
        this.userId = userId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.date = date;
        this.transactionType = transactionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Transaction{id=" + id + ", userId=" + userId + ", stockId=" + stockId + ", quantity=" + quantity + ", date=" + date + ", transactionType='" + transactionType + "'}";
    }
}
