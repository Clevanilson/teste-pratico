package br.com.iniflex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IniflexTest {

    @Test
    void deveRetornarHelloWorld() {
        Iniflex iniflex = new Iniflex();
        assertEquals("Hello World", iniflex.mensagem());
    }
}
