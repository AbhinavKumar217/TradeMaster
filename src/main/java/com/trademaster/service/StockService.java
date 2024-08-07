package com.trademaster.service;

import com.trademaster.domain.Stock;
import com.trademaster.repository.StockRepository;
import com.trademaster.repository.StockRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class StockService {
    private final StockRepository stockRepository;

    public StockService() {
        this.stockRepository = new StockRepositoryImpl();
    }

    public synchronized  void addStock(String symbol, double price, int quantity) throws SQLException {
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setPrice(price);
        stock.setQuantity(quantity);
        stockRepository.createOrUpdateStock(stock.getSymbol(), stock.getPrice(), stock.getQuantity());
    }

    public synchronized  Stock getStockBySymbol(String symbol) throws SQLException {
        return stockRepository.getStockBySymbol(symbol);
    }

    public synchronized  List<Stock> getAvailableStocks() throws SQLException {
        return stockRepository.getAllStocks();
    }
}
