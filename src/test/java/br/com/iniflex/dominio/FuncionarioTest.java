package br.com.iniflex.dominio;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FuncionarioTest {
    private static final String NOME = "Maria";
    private static final LocalDate DATA_NASCIMENTO = LocalDate.of(2000, 10, 18);
    private static final BigDecimal SALARIO = new BigDecimal("2009.44");
    private static final String FUNCAO = "Operador";

    @Test
    void comDadosValidos() {
        Funcionario funcionario = new Funcionario(NOME, DATA_NASCIMENTO, SALARIO, FUNCAO);
        assertEquals(funcionario.getNome(), NOME);
        assertEquals(funcionario.getDataNascimento(), "18/10/2000");
        assertEquals(funcionario.getSalario(), "2.009,44");
        assertEquals(funcionario.getFuncao(), FUNCAO);
    }

    @Test
    void comNomeInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Funcionario(null, DATA_NASCIMENTO, SALARIO, FUNCAO));
        assertThrows(IllegalArgumentException.class, () -> new Funcionario("", DATA_NASCIMENTO, SALARIO, FUNCAO));
        assertThrows(IllegalArgumentException.class, () -> new Funcionario(" ", DATA_NASCIMENTO, SALARIO, FUNCAO));
    }

    @Test
    void comSalarioInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Funcionario(NOME, DATA_NASCIMENTO, new BigDecimal("-1"), FUNCAO));
    }

    @Test
    void comFuncaoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Funcionario(NOME, DATA_NASCIMENTO, SALARIO, null));
        assertThrows(IllegalArgumentException.class, () -> new Funcionario(NOME, DATA_NASCIMENTO, SALARIO, ""));
        assertThrows(IllegalArgumentException.class, () -> new Funcionario(NOME, DATA_NASCIMENTO, SALARIO, " "));
    }

    @Test
    void comDataNascimentoInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Funcionario(NOME, LocalDate.now().plusDays(1), SALARIO, FUNCAO));
    }
}
