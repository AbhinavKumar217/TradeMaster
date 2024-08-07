package com.trademaster.repository;

import com.trademaster.domain.StockPriceHistory;

import java.sql.SQLException;
import java.util.List;

public interface StockPriceHistoryRepository {
    void addStockPriceHistory(StockPriceHistory history) throws SQLException;
    List<StockPriceHistory> getStockPriceHistory(String stockSymbol, int fromYear, int toYear) throws SQLException;
}
