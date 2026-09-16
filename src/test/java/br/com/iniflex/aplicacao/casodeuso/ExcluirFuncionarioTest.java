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

class ExcluirFuncionarioTest {
    private FuncionarioMemoriaRepositorio repositorio;
    private CadastrarFuncionario cadastrarFuncionario;
    private ExcluirFuncionario excluirFuncionario;
    private CadastrarFuncionario.Input maria;
    private CadastrarFuncionario.Input joao;

    @BeforeEach
    void setUp() {
        repositorio = new FuncionarioMemoriaRepositorio();
        cadastrarFuncionario = new CadastrarFuncionario(repositorio);
        excluirFuncionario = new ExcluirFuncionario(repositorio);
        maria = new CadastrarFuncionario.Input("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
        joao = new CadastrarFuncionario.Input("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");
    }

    @Test
    void excluindoValido() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        ExcluirFuncionario.Output output = excluirFuncionario.executar(new ExcluirFuncionario.Input("João"));
        assertEquals(joao.nome(), output.nome());
        assertEquals(joao.dataNascimento(), output.dataNascimento());
        assertEquals(joao.salario(), output.salario());
        assertEquals(joao.funcao(), output.funcao());
        List<Funcionario> funcionarios = repositorio.listar();
        assertEquals(1, funcionarios.size());
        assertEquals(maria.nome(), funcionarios.get(0).getNome());
    }

    @Test
    void excluindoNulo() {
        assertThrows(IllegalArgumentException.class, () -> excluirFuncionario.executar(null));
    }

    @Test
    void excluindoInexistente() {
        cadastrarFuncionario.executar(maria);
        assertThrows(IllegalArgumentException.class, () -> excluirFuncionario.executar(new ExcluirFuncionario.Input("João")));
        assertEquals(1, repositorio.listar().size());
    }
}
