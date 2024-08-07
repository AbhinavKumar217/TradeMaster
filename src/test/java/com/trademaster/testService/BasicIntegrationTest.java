package com.trademaster.testService;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BasicIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Test
    void testDatabaseConnection() {
        System.out.println("Postgres is running: " + postgresContainer.isRunning());
        System.out.println("JDBC URL: " + postgresContainer.getJdbcUrl());
    }
}