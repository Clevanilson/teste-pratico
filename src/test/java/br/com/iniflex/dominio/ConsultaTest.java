package br.com.iniflex.dominio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsultaTest {
    @Test
    void comDadosValidos() {
        Ordenacao ordenacao = new Ordenacao(Campo.NOME, Direcao.CRESCENTE);
        Consulta consulta = new Consulta(ordenacao, 1);
        assertEquals(ordenacao, consulta.getOrdenacao());
        assertEquals(1, consulta.getLimite());
    }

    @Test
    void comCamposOpcionais() {
        Consulta consulta = new Consulta(null, null);
        assertNull(consulta.getOrdenacao());
        assertNull(consulta.getLimite());
    }

    @Test
    void comLimiteInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Consulta(null, 0));
        assertThrows(IllegalArgumentException.class, () -> new Consulta(null, -1));
    }
}
