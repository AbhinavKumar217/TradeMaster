package com.trademaster.domain;

public class Stock {
    private int id;
    private String symbol;
    private double price;
    private int quantity;

    public Stock() {}

    public Stock(int id, String symbol, double price, int quantity) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Stock{id=" + id + ", symbol='" + symbol + "', price=" + price + ", quantity=" + quantity + "}";
    }
}
