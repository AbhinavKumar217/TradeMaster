package com.trademaster.service;

import com.trademaster.domain.Stock;
import com.trademaster.domain.Transaction;
import com.trademaster.domain.User;
import com.trademaster.repository.TransactionRepository;
import com.trademaster.repository.TransactionRepositoryImpl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final StockService stockService;
    private final UserService userService;

    public TransactionService(UserService userService) {
        this.transactionRepository = new TransactionRepositoryImpl();
        this.stockService = new StockService();
        this.userService = userService;
    }

    public synchronized  void buyStock(String symbol, int quantity) throws SQLException {
        User user = userService.getLoggedInUser();
        if (user == null) {
            throw new IllegalStateException("User not logged in");
        }
        
        Stock stock = stockService.getStockBySymbol(symbol);
        if (stock != null && stock.getQuantity() >= quantity) {
            Transaction transaction = new Transaction();
            transaction.setUserId(user.getId());
            transaction.setStockId(stock.getId());
            transaction.setQuantity(quantity);
            transaction.setDate(new Timestamp(System.currentTimeMillis()));
            transaction.setTransactionType("BUY");
            transactionRepository.createTransaction(transaction);

            stock.setQuantity(stock.getQuantity() - quantity);
            stockService.addStock(stock.getSymbol(), stock.getPrice(), stock.getQuantity());

            System.out.println("Stock bought successfully.");
        } else {
            System.out.println("Stock not available or insufficient quantity.");
        }
    }

    public synchronized  void sellStock(String symbol, int quantity) throws SQLException {
        User user = userService.getLoggedInUser();
        if (user == null) {
            throw new IllegalStateException("User not logged in");
        }

        Stock stock = stockService.getStockBySymbol(symbol);
        if (stock != null) {
            Transaction transaction = new Transaction();
            transaction.setUserId(user.getId());
            transaction.setStockId(stock.getId());
            transaction.setQuantity(quantity);
            transaction.setDate(new Timestamp(System.currentTimeMillis()));
            transaction.setTransactionType("SELL");
            transactionRepository.createTransaction(transaction);

            stock.setQuantity(stock.getQuantity() + quantity);
            stockService.addStock(stock.getSymbol(), stock.getPrice(), stock.getQuantity());

            System.out.println("Stock sold successfully.");
        } else {
            System.out.println("Stock not available.");
        }
    }

    public synchronized  List<Transaction> getTransactionHistory() throws SQLException {
        User user = userService.getLoggedInUser();
        if (user == null) {
            throw new IllegalStateException("User not logged in");
        }

        return transactionRepository.getTransactionsByUserId(user.getId());
    }
}
