package br.com.iniflex.dominio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrdenacaoTest {
    @Test
    void comDadosValidos() {
        Ordenacao ordenacao = new Ordenacao(Campo.NOME, Direcao.CRESCENTE);
        assertEquals(Campo.NOME, ordenacao.getCampo());
        assertEquals(Direcao.CRESCENTE, ordenacao.getDirecao());
    }

    @Test
    void comCampoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Ordenacao(null, Direcao.CRESCENTE));
    }

    @Test
    void comDirecaoInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Ordenacao(Campo.NOME, null));
    }
}
