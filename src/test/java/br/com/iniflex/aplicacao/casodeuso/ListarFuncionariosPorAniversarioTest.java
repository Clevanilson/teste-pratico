package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.infra.repositorio.FuncionarioMemoriaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListarFuncionariosPorAniversarioTest {
    private FuncionarioMemoriaRepositorio repositorio;
    private CadastrarFuncionario cadastrarFuncionario;
    private ExcluirFuncionario excluirFuncionario;
    private ListarFuncionariosPorAniversario listarFuncionariosPorAniversario;
    private CadastrarFuncionario.Input maria;
    private CadastrarFuncionario.Input joao;
    private CadastrarFuncionario.Input miguel;
    private CadastrarFuncionario.Input heitor;

    @BeforeEach
    void setUp() {
        repositorio = new FuncionarioMemoriaRepositorio();
        cadastrarFuncionario = new CadastrarFuncionario(repositorio);
        excluirFuncionario = new ExcluirFuncionario(repositorio);
        listarFuncionariosPorAniversario = new ListarFuncionariosPorAniversario(repositorio);
        maria = new CadastrarFuncionario.Input("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
        joao = new CadastrarFuncionario.Input("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");
        miguel = new CadastrarFuncionario.Input("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor");
        heitor = new CadastrarFuncionario.Input("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador");
    }

    @Test
    void listandoValido() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        cadastrarFuncionario.executar(miguel);
        cadastrarFuncionario.executar(heitor);
        ListarFuncionariosPorAniversario.Output output = listarFuncionariosPorAniversario.executar(
                new ListarFuncionariosPorAniversario.Input(List.of(10, 12))
        );
        List<ListarFuncionariosPorAniversario.Output.Funcionario> funcionarios = output.funcionarios();
        assertEquals(2, funcionarios.size());
        assertEquals(maria.nome(), funcionarios.get(0).nome());
        assertEquals(maria.dataNascimento(), funcionarios.get(0).dataNascimento());
        assertEquals(maria.salario(), funcionarios.get(0).salario());
        assertEquals(maria.funcao(), funcionarios.get(0).funcao());
        assertEquals(miguel.nome(), funcionarios.get(1).nome());
        assertEquals(miguel.dataNascimento(), funcionarios.get(1).dataNascimento());
        assertEquals(miguel.salario(), funcionarios.get(1).salario());
        assertEquals(miguel.funcao(), funcionarios.get(1).funcao());
    }

    @Test
    void listandoVazio() {
        cadastrarFuncionario.executar(joao);
        ListarFuncionariosPorAniversario.Output output = listarFuncionariosPorAniversario.executar(
                new ListarFuncionariosPorAniversario.Input(List.of(10, 12))
        );
        assertTrue(output.funcionarios().isEmpty());
    }

    @Test
    void listandoAposExclusao() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(miguel);
        excluirFuncionario.executar(new ExcluirFuncionario.Input("Maria"));
        ListarFuncionariosPorAniversario.Output output = listarFuncionariosPorAniversario.executar(
                new ListarFuncionariosPorAniversario.Input(List.of(10, 12))
        );
        assertEquals(1, output.funcionarios().size());
        assertEquals(miguel.nome(), output.funcionarios().get(0).nome());
    }

    @Test
    void listandoNulo() {
        assertThrows(IllegalArgumentException.class, () -> listarFuncionariosPorAniversario.executar(null));
    }

    @Test
    void listandoMesesInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> listarFuncionariosPorAniversario.executar(
                new ListarFuncionariosPorAniversario.Input(null)
        ));
        assertThrows(IllegalArgumentException.class, () -> listarFuncionariosPorAniversario.executar(
                new ListarFuncionariosPorAniversario.Input(List.of())
        ));
        assertThrows(IllegalArgumentException.class, () -> listarFuncionariosPorAniversario.executar(
                new ListarFuncionariosPorAniversario.Input(List.of(0))
        ));
        assertThrows(IllegalArgumentException.class, () -> listarFuncionariosPorAniversario.executar(
                new ListarFuncionariosPorAniversario.Input(List.of(13))
        ));
    }
}
