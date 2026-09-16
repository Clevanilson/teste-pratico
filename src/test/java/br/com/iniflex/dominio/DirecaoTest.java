package br.com.iniflex.dominio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DirecaoTest {
    @Test
    void comDadosValidos() {
        assertEquals("CRESCENTE", new Direcao("CRESCENTE").getValor());
        assertEquals("DECRESCENTE", new Direcao("decrescente").getValor());
        assertEquals(Direcao.CRESCENTE, new Direcao(" crescente "));
        assertEquals(Direcao.DECRESCENTE, new Direcao("DECRESCENTE"));
    }

    @Test
    void comValorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Direcao(null));
        assertThrows(IllegalArgumentException.class, () -> new Direcao(""));
        assertThrows(IllegalArgumentException.class, () -> new Direcao(" "));
        assertThrows(IllegalArgumentException.class, () -> new Direcao("ASC"));
    }
}
