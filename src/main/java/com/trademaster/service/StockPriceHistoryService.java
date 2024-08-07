package com.trademaster.service;

import com.trademaster.domain.StockPriceHistory;
import com.trademaster.repository.StockPriceHistoryRepository;
import com.trademaster.repository.StockPriceHistoryRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class StockPriceHistoryService {
    private final StockPriceHistoryRepository stockPriceHistoryRepository;

    public StockPriceHistoryService() {
        this.stockPriceHistoryRepository = new StockPriceHistoryRepositoryImpl();
    }

    public void addStockPriceHistory(StockPriceHistory history) throws SQLException {
        stockPriceHistoryRepository.addStockPriceHistory(history);
    }

    public double calculateAverageStockPrice(String stockSymbol, int fromYear, int toYear) throws SQLException {
        List<StockPriceHistory> histories = stockPriceHistoryRepository.getStockPriceHistory(stockSymbol, fromYear, toYear);
        if (histories.isEmpty()) {
            throw new IllegalArgumentException("No stock price history found for the specified range.");
        }

        double total = 0;
        for (StockPriceHistory history : histories) {
            total += history.getPrice();
        }

        return total / histories.size();
    }
}
