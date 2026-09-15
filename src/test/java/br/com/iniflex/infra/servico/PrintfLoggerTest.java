package br.com.iniflex.infra.servico;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintfLoggerTest {
    private final PrintStream saidaOriginal = System.out;
    private ByteArrayOutputStream buffer;

    @BeforeEach
    void setUp() {
        buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
    }

    @AfterEach
    void tearDown() {
        System.setOut(saidaOriginal);
    }

    @Test
    void logandoComFormatoPrintf() {
        new PrintfLogger().log("Funcionário %s, idade %d", "Maria", 25);
        assertEquals("Funcionário Maria, idade 25", buffer.toString());
    }
}
