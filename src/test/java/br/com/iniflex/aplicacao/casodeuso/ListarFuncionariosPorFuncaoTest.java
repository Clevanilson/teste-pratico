package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.infra.repositorio.FuncionarioMemoriaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListarFuncionariosPorFuncaoTest {
    private FuncionarioMemoriaRepositorio repositorio;
    private CadastrarFuncionario cadastrarFuncionario;
    private ExcluirFuncionario excluirFuncionario;
    private ListarFuncionariosPorFuncao listarFuncionariosPorFuncao;
    private CadastrarFuncionario.Input maria;
    private CadastrarFuncionario.Input joao;
    private CadastrarFuncionario.Input caio;

    @BeforeEach
    void setUp() {
        repositorio = new FuncionarioMemoriaRepositorio();
        cadastrarFuncionario = new CadastrarFuncionario(repositorio);
        excluirFuncionario = new ExcluirFuncionario(repositorio);
        listarFuncionariosPorFuncao = new ListarFuncionariosPorFuncao(repositorio);
        maria = new CadastrarFuncionario.Input("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
        joao = new CadastrarFuncionario.Input("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");
        caio = new CadastrarFuncionario.Input("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador");
    }

    @Test
    void listandoValido() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        cadastrarFuncionario.executar(caio);
        ListarFuncionariosPorFuncao.Output output = listarFuncionariosPorFuncao.executar();
        Map<String, List<ListarFuncionariosPorFuncao.Output.Funcionario>> funcionariosPorFuncao = output.funcionariosPorFuncao();
        assertEquals(2, funcionariosPorFuncao.size());
        List<ListarFuncionariosPorFuncao.Output.Funcionario> operadores = funcionariosPorFuncao.get("Operador");
        assertEquals(2, operadores.size());
        assertEquals(maria.nome(), operadores.get(0).nome());
        assertEquals(maria.dataNascimento(), operadores.get(0).dataNascimento());
        assertEquals(maria.salario(), operadores.get(0).salario());
        assertEquals(maria.funcao(), operadores.get(0).funcao());
        assertEquals(joao.nome(), operadores.get(1).nome());
        assertEquals(joao.dataNascimento(), operadores.get(1).dataNascimento());
        assertEquals(joao.salario(), operadores.get(1).salario());
        assertEquals(joao.funcao(), operadores.get(1).funcao());
        List<ListarFuncionariosPorFuncao.Output.Funcionario> coordenadores = funcionariosPorFuncao.get("Coordenador");
        assertEquals(1, coordenadores.size());
        assertEquals(caio.nome(), coordenadores.get(0).nome());
        assertEquals(caio.dataNascimento(), coordenadores.get(0).dataNascimento());
        assertEquals(caio.salario(), coordenadores.get(0).salario());
        assertEquals(caio.funcao(), coordenadores.get(0).funcao());
    }

    @Test
    void listandoVazio() {
        ListarFuncionariosPorFuncao.Output output = listarFuncionariosPorFuncao.executar();
        assertTrue(output.funcionariosPorFuncao().isEmpty());
    }

    @Test
    void listandoAposExclusao() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        cadastrarFuncionario.executar(caio);
        excluirFuncionario.executar(new ExcluirFuncionario.Input("João"));
        excluirFuncionario.executar(new ExcluirFuncionario.Input("Caio"));
        ListarFuncionariosPorFuncao.Output output = listarFuncionariosPorFuncao.executar();
        Map<String, List<ListarFuncionariosPorFuncao.Output.Funcionario>> funcionariosPorFuncao = output.funcionariosPorFuncao();
        assertEquals(1, funcionariosPorFuncao.size());
        assertEquals(1, funcionariosPorFuncao.get("Operador").size());
        assertEquals(maria.nome(), funcionariosPorFuncao.get("Operador").get(0).nome());
        assertFalse(funcionariosPorFuncao.containsKey("Coordenador"));
    }
}
