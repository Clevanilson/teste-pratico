package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.infra.repositorio.FuncionarioMemoriaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListarTotalSalariosTest {
    private FuncionarioMemoriaRepositorio repositorio;
    private CadastrarFuncionario cadastrarFuncionario;
    private ExcluirFuncionario excluirFuncionario;
    private ListarTotalSalarios listarTotalSalarios;
    private CadastrarFuncionario.Input maria;
    private CadastrarFuncionario.Input joao;

    @BeforeEach
    void setUp() {
        repositorio = new FuncionarioMemoriaRepositorio();
        cadastrarFuncionario = new CadastrarFuncionario(repositorio);
        excluirFuncionario = new ExcluirFuncionario(repositorio);
        listarTotalSalarios = new ListarTotalSalarios(repositorio);
        maria = new CadastrarFuncionario.Input("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
        joao = new CadastrarFuncionario.Input("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");
    }

    @Test
    void listandoValido() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        ListarTotalSalarios.Output output = listarTotalSalarios.executar();
        assertEquals(new BigDecimal("4293.82"), output.total());
    }

    @Test
    void listandoVazio() {
        ListarTotalSalarios.Output output = listarTotalSalarios.executar();
        assertEquals(new BigDecimal("0.00"), output.total());
    }

    @Test
    void listandoAposExclusao() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        excluirFuncionario.executar(new ExcluirFuncionario.Input("João"));
        ListarTotalSalarios.Output output = listarTotalSalarios.executar();
        assertEquals(maria.salario(), output.total());
    }
}
