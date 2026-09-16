package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.infra.repositorio.FuncionarioMemoriaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListarFuncionarioMaisVelhoTest {
    private FuncionarioMemoriaRepositorio repositorio;
    private CadastrarFuncionario cadastrarFuncionario;
    private ExcluirFuncionario excluirFuncionario;
    private ListarFuncionarioMaisVelho listarFuncionarioMaisVelho;
    private CadastrarFuncionario.Input maria;
    private CadastrarFuncionario.Input joao;
    private CadastrarFuncionario.Input caio;

    @BeforeEach
    void setUp() {
        repositorio = new FuncionarioMemoriaRepositorio();
        cadastrarFuncionario = new CadastrarFuncionario(repositorio);
        excluirFuncionario = new ExcluirFuncionario(repositorio);
        listarFuncionarioMaisVelho = new ListarFuncionarioMaisVelho(repositorio);
        maria = new CadastrarFuncionario.Input("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
        joao = new CadastrarFuncionario.Input("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");
        caio = new CadastrarFuncionario.Input("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador");
    }

    @Test
    void listandoValido() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        cadastrarFuncionario.executar(caio);
        ListarFuncionarioMaisVelho.Output output = listarFuncionarioMaisVelho.executar();
        List<ListarFuncionarioMaisVelho.Output.Funcionario> funcionarios = output.funcionarios();
        assertEquals(1, funcionarios.size());
        assertEquals(caio.nome(), funcionarios.get(0).nome());
        assertEquals(Period.between(caio.dataNascimento(), LocalDate.now()).getYears(), funcionarios.get(0).idade());
    }

    @Test
    void listandoVazio() {
        ListarFuncionarioMaisVelho.Output output = listarFuncionarioMaisVelho.executar();
        assertTrue(output.funcionarios().isEmpty());
    }

    @Test
    void listandoAposExclusao() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(caio);
        excluirFuncionario.executar(new ExcluirFuncionario.Input("Caio"));
        ListarFuncionarioMaisVelho.Output output = listarFuncionarioMaisVelho.executar();
        assertEquals(1, output.funcionarios().size());
        assertEquals(maria.nome(), output.funcionarios().get(0).nome());
        assertEquals(Period.between(maria.dataNascimento(), LocalDate.now()).getYears(), output.funcionarios().get(0).idade());
    }
}
