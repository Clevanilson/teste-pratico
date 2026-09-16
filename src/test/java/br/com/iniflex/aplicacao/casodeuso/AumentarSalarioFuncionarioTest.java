package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.dominio.Funcionario;
import br.com.iniflex.infra.repositorio.FuncionarioMemoriaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AumentarSalarioFuncionarioTest {
    private FuncionarioMemoriaRepositorio repositorio;
    private CadastrarFuncionario cadastrarFuncionario;
    private AumentarSalarioFuncionario aumentarSalarioFuncionario;
    private CadastrarFuncionario.Input maria;
    private CadastrarFuncionario.Input joao;

    @BeforeEach
    void setUp() {
        repositorio = new FuncionarioMemoriaRepositorio();
        cadastrarFuncionario = new CadastrarFuncionario(repositorio);
        aumentarSalarioFuncionario = new AumentarSalarioFuncionario(repositorio);
        maria = new CadastrarFuncionario.Input("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
        joao = new CadastrarFuncionario.Input("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");
    }

    @Test
    void aumentandoValido() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        AumentarSalarioFuncionario.Output output = aumentarSalarioFuncionario.executar(
                new AumentarSalarioFuncionario.Input(10)
        );
        List<AumentarSalarioFuncionario.Output.Funcionario> funcionarios = output.funcionarios();
        assertEquals(2, funcionarios.size());
        assertEquals(maria.nome(), funcionarios.get(0).nome());
        assertEquals(new BigDecimal("2210.38"), funcionarios.get(0).salario());
        assertEquals(joao.nome(), funcionarios.get(1).nome());
        assertEquals(new BigDecimal("2512.82"), funcionarios.get(1).salario());
        List<Funcionario> persistidos = repositorio.listar();
        assertEquals(new BigDecimal("2210.38"), persistidos.get(0).getSalario());
        assertEquals(new BigDecimal("2512.82"), persistidos.get(1).getSalario());
    }

    @Test
    void aumentandoVazio() {
        AumentarSalarioFuncionario.Output output = aumentarSalarioFuncionario.executar(
                new AumentarSalarioFuncionario.Input(10)
        );
        assertTrue(output.funcionarios().isEmpty());
    }

    @Test
    void aumentandoNulo() {
        assertThrows(IllegalArgumentException.class, () -> aumentarSalarioFuncionario.executar(null));
    }

    @Test
    void aumentandoPercentualInvalido() {
        cadastrarFuncionario.executar(maria);
        assertThrows(
                IllegalArgumentException.class,
                () -> aumentarSalarioFuncionario.executar(new AumentarSalarioFuncionario.Input(-1))
        );
        assertEquals(maria.salario(), repositorio.listar().get(0).getSalario());
    }
}
