package com.rabin.asynchrons.multithreading.practise.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;

class AsyncConfigTest {

    private AsyncConfig asyncConfigUnderTest;

    @BeforeEach
    void setUp() {
        asyncConfigUnderTest = new AsyncConfig();
    }

    @Test
    void testTaskExecutor() {
        // Setup
        // Run the test
        final Executor result = asyncConfigUnderTest.taskExecutor();

        // Verify the results
    }
}
