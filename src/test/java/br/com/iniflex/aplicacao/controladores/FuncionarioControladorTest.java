package br.com.iniflex.aplicacao.controladores;

import br.com.iniflex.aplicacao.casodeuso.CadastrarFuncionario;
import br.com.iniflex.dominio.Funcionario;
import br.com.iniflex.infra.repositorio.FuncionarioMemoriaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FuncionarioControladorTest {
    private FuncionarioMemoriaRepositorio repositorio;
    private FuncionarioControlador controlador;
    private CadastrarFuncionario.Input maria;

    @BeforeEach
    void setUp() {
        repositorio = new FuncionarioMemoriaRepositorio();
        controlador = new FuncionarioControlador(new CadastrarFuncionario(repositorio));
        maria = new CadastrarFuncionario.Input("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
    }

    @Test
    void cadastrandoValido() {
        controlador.cadastrarFuncionario(maria);
        List<Funcionario> funcionarios = repositorio.listar();
        assertEquals(1, funcionarios.size());
        assertEquals(maria.nome(), funcionarios.get(0).getNome());
        assertEquals(maria.dataNascimento(), funcionarios.get(0).getDataNascimento());
        assertEquals(maria.salario(), funcionarios.get(0).getSalario());
        assertEquals(maria.funcao(), funcionarios.get(0).getFuncao());
    }
}
