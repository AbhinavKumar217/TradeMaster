package com.trademaster.app;

import com.trademaster.service.StockPriceHistoryService;
import com.trademaster.service.TransactionService;
import com.trademaster.service.UserService;
import com.trademaster.domain.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TradeMasterApp {
    private static UserService userService = new UserService();
    private static TransactionService transactionService = new TransactionService(userService);
    private static StockPriceHistoryService stockPriceHistoryService = new StockPriceHistoryService();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Buy Stock");
            System.out.println("4. Sell Stock");
            System.out.println("5. View Transaction History");
            System.out.println("6. Calculate Average Stock Price");
            System.out.println("7. Logout");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        userService.registerUser(username, password);
                        System.out.println("User registered successfully.");
                        break;
                    case 2:
                        System.out.print("Enter username: ");
                        String loginUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = scanner.nextLine();
                        if (userService.login(loginUsername, loginPassword)) {
                            System.out.println("Login successful.");
                        } else {
                            System.out.println("Invalid username or password.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter stock symbol to buy: ");
                        String buySymbol = scanner.nextLine();
                        System.out.print("Enter quantity to buy: ");
                        int buyQuantity = scanner.nextInt();
                        transactionService.buyStock(buySymbol, buyQuantity);
                        break;
                    case 4:
                        System.out.print("Enter stock symbol to sell: ");
                        String sellSymbol = scanner.nextLine();
                        System.out.print("Enter quantity to sell: ");
                        int sellQuantity = scanner.nextInt();
                        transactionService.sellStock(sellSymbol, sellQuantity);
                        break;
                    case 5:
                        List<Transaction> transactions = transactionService.getTransactionHistory();
                        transactions.forEach(System.out::println);
                        break;
                    case 6:
                        System.out.print("Enter stock symbol: ");
                        String stockSymbol = scanner.nextLine();
                        System.out.print("Enter start year: ");
                        int fromYear = scanner.nextInt();
                        System.out.print("Enter end year: ");
                        int toYear = scanner.nextInt();
                        double averagePrice = stockPriceHistoryService.calculateAverageStockPrice(stockSymbol, fromYear, toYear);
                        System.out.println("Average stock price from " + fromYear + " to " + toYear + " is: " + averagePrice);
                        break;
                    case 7:
                        userService.logout();
                        System.out.println("Logged out successfully.");
                        break;
                    case 8:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }
}
