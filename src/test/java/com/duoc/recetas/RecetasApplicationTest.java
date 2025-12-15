package com.duoc.recetas;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


import org.junit.jupiter.api.Test;

class RecetasApplicationTest {

    @Test
    void mainRunsWithoutExceptionWhenSkipRunTrue() {
        System.setProperty("app.test.skipRun", "true");
        String[] args = {};
        assertDoesNotThrow(() -> RecetasApplication.main(args));
    }



    @Test
    void logStartupDoesNotThrow() {
        assertDoesNotThrow(() -> {
            var method = RecetasApplication.class.getDeclaredMethod("logStartup");
            method.setAccessible(true);
            method.invoke(null);
        });
    }
}