package com.trademaster.repository;

import com.trademaster.domain.Stock;

import java.sql.SQLException;
import java.util.List;

public interface StockRepository {
    void createOrUpdateStock(String symbol, double price, int quantity) throws SQLException;
    Stock getStockBySymbol(String symbol) throws SQLException;
    List<Stock> getAllStocks() throws SQLException;
}
