package br.com.iniflex.dominio;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

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
        assertEquals(DATA_NASCIMENTO, funcionario.getDataNascimento());
        assertEquals(SALARIO, funcionario.getSalario());
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

    @Test
    void aumentarSalarioValido() {
        Funcionario funcionario = new Funcionario(NOME, DATA_NASCIMENTO, SALARIO, FUNCAO);
        funcionario.aumentarSalario(10);
        assertEquals(new BigDecimal("2210.38"), funcionario.getSalario());
    }

    @Test
    void aumentarSalarioInvalido() {
        Funcionario funcionario = new Funcionario(NOME, DATA_NASCIMENTO, SALARIO, FUNCAO);
        assertThrows(IllegalArgumentException.class, () -> funcionario.aumentarSalario(-1));
        assertEquals(SALARIO, funcionario.getSalario());
    }

    @Test
    void quantidadeSalariosMinimos() {
        Funcionario funcionario = new Funcionario(NOME, DATA_NASCIMENTO, SALARIO, FUNCAO);
        assertEquals(new BigDecimal("1.66"), funcionario.getQuantidadeSalariosMinimos());
    }

    @Test
    void quantidadeSalariosMinimosExata() {
        Funcionario funcionario = new Funcionario(NOME, DATA_NASCIMENTO, new BigDecimal("2424.00"), FUNCAO);
        assertEquals(new BigDecimal("2.00"), funcionario.getQuantidadeSalariosMinimos());
    }

}
