package com.trademaster.testService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseIntegrationTest {

    @Container
    protected final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    protected Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );
        setupDatabase();
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE users, stocks, transactions, stock_price_history RESTART IDENTITY;");
        }
        connection.close();
    }

    protected void setupDatabase() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, username VARCHAR(50), password VARCHAR(50));");
            statement.execute("CREATE TABLE IF NOT EXISTS stocks (id SERIAL PRIMARY KEY, symbol VARCHAR(10), price DOUBLE PRECISION, quantity INTEGER);");
            statement.execute("CREATE TABLE IF NOT EXISTS transactions (id SERIAL PRIMARY KEY, user_id INTEGER, stock_id INTEGER, quantity INTEGER, date TIMESTAMP, transaction_type VARCHAR(10));");
            statement.execute("CREATE TABLE IF NOT EXISTS stock_price_history (id SERIAL PRIMARY KEY, stock_id INTEGER, price DOUBLE PRECISION, date TIMESTAMP);");
        }
    }
}
