package com.trademaster.testService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserConcurrencyTest extends BaseIntegrationTest {

    private static final int THREAD_COUNT = 10;

    @BeforeEach
    @Override
    void setUp() throws SQLException {
        super.setUp();
    }

    @AfterEach
    @Override
    void tearDown() throws SQLException {
        super.tearDown();
    }

    @Test
    void testConcurrentUserCreation() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        Future<?>[] futures = new Future[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int userId = i;
            futures[i] = executor.submit(() -> {
                try (Connection conn = DriverManager.getConnection(
                        postgresContainer.getJdbcUrl(),
                        postgresContainer.getUsername(),
                        postgresContainer.getPassword());
                     Statement stmt = conn.createStatement()) {

                    int rowsAffected = stmt.executeUpdate(
                            "INSERT INTO users (username, password) VALUES ('user" + userId + "', 'password" + userId + "')"
                    );
                    assertEquals(1, rowsAffected, "Expected one row to be inserted for user" + userId);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executor.shutdown();
    }
}
