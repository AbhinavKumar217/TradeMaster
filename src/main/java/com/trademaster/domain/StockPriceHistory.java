package com.trademaster.domain;

public class StockPriceHistory {
    private int id;
    private String stockSymbol;
    private double price;
    private int year;

    public StockPriceHistory() {}

    public StockPriceHistory(int id, String stockSymbol, double price, int year) {
        this.id = id;
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "StockPriceHistory{id=" + id + ", stockSymbol='" + stockSymbol + "', price=" + price + ", year=" + year + "}";
    }
}
