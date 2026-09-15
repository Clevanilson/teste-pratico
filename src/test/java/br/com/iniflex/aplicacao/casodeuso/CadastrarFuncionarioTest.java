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

class CadastrarFuncionarioTest {
    private FuncionarioMemoriaRepositorio repositorio;
    private CadastrarFuncionario cadastrarFuncionario;
    private CadastrarFuncionario.Input maria;
    private CadastrarFuncionario.Input joao;

    @BeforeEach
    void setUp() {
        repositorio = new FuncionarioMemoriaRepositorio();
        cadastrarFuncionario = new CadastrarFuncionario(repositorio);
        maria = new CadastrarFuncionario.Input("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
        joao = new CadastrarFuncionario.Input("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");
    }

    @Test
    void cadastrandoValido() {
        CadastrarFuncionario.Output output = cadastrarFuncionario.executar(maria);
        assertEquals(maria.nome(), output.nome());
        assertEquals(maria.dataNascimento(), output.dataNascimento());
        assertEquals(maria.salario(), output.salario());
        assertEquals(maria.funcao(), output.funcao());
        List<Funcionario> funcionarios = repositorio.listar();
        assertEquals(1, funcionarios.size());
        assertEquals(maria.nome(), funcionarios.get(0).getNome());
        assertEquals(maria.dataNascimento(), funcionarios.get(0).getDataNascimento());
        assertEquals(maria.salario(), funcionarios.get(0).getSalario());
        assertEquals(maria.funcao(), funcionarios.get(0).getFuncao());
    }

    @Test
    void cadastrandoNaOrdem() {
        cadastrarFuncionario.executar(maria);
        cadastrarFuncionario.executar(joao);
        List<Funcionario> funcionarios = repositorio.listar();
        assertEquals(2, funcionarios.size());
        assertEquals("Maria", funcionarios.get(0).getNome());
        assertEquals("João", funcionarios.get(1).getNome());
    }

    @Test
    void cadastrandoNulo() {
        assertThrows(IllegalArgumentException.class, () -> cadastrarFuncionario.executar(null));
    }
}
