package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.dominio.Campo;
import br.com.iniflex.dominio.Direcao;
import br.com.iniflex.dominio.Ordenacao;
import br.com.iniflex.infra.repositorio.FuncionarioMemoriaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListarFuncionariosTest {
    private FuncionarioMemoriaRepositorio repositorio;
    private CadastrarFuncionario cadastrarFuncionario;
    private ExcluirFuncionario excluirFuncionario;
    private ListarFuncionarios listarFuncionarios;
    private CadastrarFuncionario.Input maria;
    private CadastrarFuncionario.Input joao;
    private CadastrarFuncionario.Input caio;

    @BeforeEach
    void setUp() {
        repositorio = new FuncionarioMemoriaRepositorio();
        cadastrarFuncionario = new CadastrarFuncionario(repositorio);
        excluirFuncionario = new ExcluirFuncionario(repositorio);
        listarFuncionarios = new ListarFuncionarios(repositorio);
        maria = new CadastrarFuncionario.Input("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
        joao = new CadastrarFuncionario.Input("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");
        caio = new CadastrarFuncionario.Input("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador");
    }

    @Test
    void listandoValido() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        ListarFuncionarios.Output output = listarFuncionarios.executar();
        List<ListarFuncionarios.Output.Funcionario> funcionarios = output.funcionarios();
        assertEquals(2, funcionarios.size());
        assertEquals(maria.nome(), funcionarios.get(0).nome());
        assertEquals(maria.dataNascimento(), funcionarios.get(0).dataNascimento());
        assertEquals(maria.salario(), funcionarios.get(0).salario());
        assertEquals(maria.funcao(), funcionarios.get(0).funcao());
        assertEquals(joao.nome(), funcionarios.get(1).nome());
        assertEquals(joao.dataNascimento(), funcionarios.get(1).dataNascimento());
        assertEquals(joao.salario(), funcionarios.get(1).salario());
        assertEquals(joao.funcao(), funcionarios.get(1).funcao());
    }

    @Test
    void listandoVazio() {
        ListarFuncionarios.Output output = listarFuncionarios.executar();
        assertTrue(output.funcionarios().isEmpty());
    }

    @Test
    void listandoAposExclusao() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        excluirFuncionario.executar(new ExcluirFuncionario.Input("João"));
        ListarFuncionarios.Output output = listarFuncionarios.executar();
        assertEquals(1, output.funcionarios().size());
        assertEquals(maria.nome(), output.funcionarios().get(0).nome());
    }

    @Test
    void listandoOrdenadoPorNome() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        cadastrarFuncionario.executar(caio);
        ListarFuncionarios.Output output = listarFuncionarios.executar(new Ordenacao(Campo.NOME, Direcao.CRESCENTE));
        List<ListarFuncionarios.Output.Funcionario> funcionarios = output.funcionarios();
        assertEquals(3, funcionarios.size());
        assertEquals(caio.nome(), funcionarios.get(0).nome());
        assertEquals(caio.dataNascimento(), funcionarios.get(0).dataNascimento());
        assertEquals(caio.salario(), funcionarios.get(0).salario());
        assertEquals(caio.funcao(), funcionarios.get(0).funcao());
        assertEquals(joao.nome(), funcionarios.get(1).nome());
        assertEquals(joao.dataNascimento(), funcionarios.get(1).dataNascimento());
        assertEquals(joao.salario(), funcionarios.get(1).salario());
        assertEquals(joao.funcao(), funcionarios.get(1).funcao());
        assertEquals(maria.nome(), funcionarios.get(2).nome());
        assertEquals(maria.dataNascimento(), funcionarios.get(2).dataNascimento());
        assertEquals(maria.salario(), funcionarios.get(2).salario());
        assertEquals(maria.funcao(), funcionarios.get(2).funcao());
    }
}
