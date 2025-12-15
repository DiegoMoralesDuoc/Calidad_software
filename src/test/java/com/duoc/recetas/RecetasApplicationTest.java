package com.duoc.recetas;

import org.junit.jupiter.api.Test;

class RecetasApplicationTest {

    @Test
    void mainRunsWithoutExceptionWhenSkipRunTrue() {
        System.setProperty("app.test.skipRun", "true");
        String[] args = {};
        RecetasApplication.main(args);
    }

    @Test
    void logStartupDoesNotThrow() throws Exception {
        var method = RecetasApplication.class.getDeclaredMethod("logStartup");
        method.setAccessible(true);
        method.invoke(null);
    }
}