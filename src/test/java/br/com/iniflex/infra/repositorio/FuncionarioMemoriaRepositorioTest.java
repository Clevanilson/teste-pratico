package br.com.iniflex.infra.repositorio;

import br.com.iniflex.dominio.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FuncionarioMemoriaRepositorioTest {
    private FuncionarioMemoriaRepositorio repositorio;
    private Funcionario maria;
    private Funcionario joao;
    private Funcionario caio;

    @BeforeEach
    void setUp() {
        repositorio = new FuncionarioMemoriaRepositorio();
        maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
        joao = new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");
        caio = new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador");
    }

    @Test
    void salvandoInexistente() {
        repositorio.salvar(maria);
        repositorio.salvar(joao);
        repositorio.salvar(caio);
        List<Funcionario> funcionarios = repositorio.listar();
        assertEquals(3, funcionarios.size());
        assertEquals("Maria", funcionarios.get(0).getNome());
        assertEquals("João", funcionarios.get(1).getNome());
        assertEquals("Caio", funcionarios.get(2).getNome());
        Map<String, List<Funcionario>> porFuncao = repositorio.listarPorFuncao();
        assertEquals(2, porFuncao.get("Operador").size());
        assertEquals("Maria", porFuncao.get("Operador").get(0).getNome());
        assertEquals("João", porFuncao.get("Operador").get(1).getNome());
        assertEquals(1, porFuncao.get("Coordenador").size());
        assertEquals("Caio", porFuncao.get("Coordenador").get(0).getNome());
    }

    @Test
    void removendoExistente() {
        repositorio.salvar(maria);
        repositorio.salvar(joao);
        repositorio.salvar(caio);
        repositorio.remover("João");
        List<Funcionario> funcionarios = repositorio.listar();
        assertEquals(2, funcionarios.size());
        assertFalse(funcionarios.stream().anyMatch(funcionario -> funcionario.getNome().equals("João")));
        Map<String, List<Funcionario>> porFuncao = repositorio.listarPorFuncao();
        assertEquals(1, porFuncao.get("Operador").size());
        assertEquals("Maria", porFuncao.get("Operador").get(0).getNome());
        assertTrue(porFuncao.containsKey("Coordenador"));
    }

    @Test
    void removendoUltimoDaFuncao() {
        repositorio.salvar(caio);
        repositorio.remover("Caio");
        assertTrue(repositorio.listar().isEmpty());
        assertFalse(repositorio.listarPorFuncao().containsKey("Coordenador"));
    }

    @Test
    void salvandoComNomeJaExistente() {
        repositorio.salvar(maria);
        repositorio.salvar(joao);
        Funcionario mariaAtualizada = new Funcionario(
                "Maria",
                LocalDate.of(2000, 10, 18),
                new BigDecimal("2210.38"),
                "Gerente"
        );
        repositorio.salvar(mariaAtualizada);
        List<Funcionario> funcionarios = repositorio.listar();
        assertEquals(2, funcionarios.size());
        Funcionario encontrada = funcionarios.get(0);
        assertEquals("Maria", encontrada.getNome());
        assertEquals("2.210,38", encontrada.getSalario());
        assertEquals("Gerente", encontrada.getFuncao());
        Map<String, List<Funcionario>> porFuncao = repositorio.listarPorFuncao();
        assertFalse(porFuncao.containsKey("Operador") && porFuncao.get("Operador").stream()
                .anyMatch(funcionario -> funcionario.getNome().equals("Maria")));
        assertEquals("Maria", porFuncao.get("Gerente").get(0).getNome());
        assertEquals(1, porFuncao.get("Operador").size());
        assertEquals("João", porFuncao.get("Operador").get(0).getNome());
    }

    @Test
    void removendoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> repositorio.remover("João"));
    }
}
