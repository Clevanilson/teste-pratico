package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.infra.repositorio.FuncionarioMemoriaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListarQuantidadeSalariosMinimosTest {
    private FuncionarioMemoriaRepositorio repositorio;
    private CadastrarFuncionario cadastrarFuncionario;
    private ExcluirFuncionario excluirFuncionario;
    private ListarQuantidadeSalariosMinimos listarQuantidadeSalariosMinimos;
    private CadastrarFuncionario.Input maria;
    private CadastrarFuncionario.Input joao;

    @BeforeEach
    void setUp() {
        repositorio = new FuncionarioMemoriaRepositorio();
        cadastrarFuncionario = new CadastrarFuncionario(repositorio);
        excluirFuncionario = new ExcluirFuncionario(repositorio);
        listarQuantidadeSalariosMinimos = new ListarQuantidadeSalariosMinimos(repositorio);
        maria = new CadastrarFuncionario.Input("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
        joao = new CadastrarFuncionario.Input("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");
    }

    @Test
    void listandoValido() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        ListarQuantidadeSalariosMinimos.Output output = listarQuantidadeSalariosMinimos.executar();
        List<ListarQuantidadeSalariosMinimos.Output.Funcionario> funcionarios = output.funcionarios();
        assertEquals(2, funcionarios.size());
        assertEquals(maria.nome(), funcionarios.get(0).nome());
        assertEquals(new BigDecimal("1.66"), funcionarios.get(0).quantidadeSalariosMinimos());
        assertEquals(joao.nome(), funcionarios.get(1).nome());
        assertEquals(new BigDecimal("1.88"), funcionarios.get(1).quantidadeSalariosMinimos());
    }

    @Test
    void listandoVazio() {
        ListarQuantidadeSalariosMinimos.Output output = listarQuantidadeSalariosMinimos.executar();
        assertTrue(output.funcionarios().isEmpty());
    }

    @Test
    void listandoAposExclusao() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        excluirFuncionario.executar(new ExcluirFuncionario.Input("João"));
        ListarQuantidadeSalariosMinimos.Output output = listarQuantidadeSalariosMinimos.executar();
        assertEquals(1, output.funcionarios().size());
        assertEquals(maria.nome(), output.funcionarios().get(0).nome());
        assertEquals(new BigDecimal("1.66"), output.funcionarios().get(0).quantidadeSalariosMinimos());
    }
}
