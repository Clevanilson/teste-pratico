package br.com.iniflex.aplicacao.controladores;

import br.com.iniflex.aplicacao.casodeuso.CadastrarFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ExcluirFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarioMaisVelho;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarios;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorAniversario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorFuncao;
import br.com.iniflex.aplicacao.casodeuso.ListarQuantidadeSalariosMinimos;
import br.com.iniflex.aplicacao.views.FuncionarioView;
import br.com.iniflex.dominio.Campo;
import br.com.iniflex.dominio.Direcao;
import br.com.iniflex.dominio.Funcionario;
import br.com.iniflex.dominio.Ordenacao;
import br.com.iniflex.infra.repositorio.FuncionarioMemoriaRepositorio;
import br.com.iniflex.infra.servico.LoggerFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FuncionarioControladorTest {
    private FuncionarioMemoriaRepositorio repositorio;
    private LoggerFake logger;
    private FuncionarioControlador controlador;
    private CadastrarFuncionario.Input maria;

    @BeforeEach
    void setUp() {
        repositorio = new FuncionarioMemoriaRepositorio();
        logger = new LoggerFake();
        controlador = new FuncionarioControlador(
                new CadastrarFuncionario(repositorio),
                new ExcluirFuncionario(repositorio),
                new ListarFuncionarios(repositorio),
                new ListarFuncionariosPorFuncao(repositorio),
                new ListarFuncionariosPorAniversario(repositorio),
                new ListarFuncionarioMaisVelho(repositorio),
                new ListarQuantidadeSalariosMinimos(repositorio),
                new FuncionarioView(logger)
        );
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
        assertEquals(
                "OK: Funcionário cadastrado" + System.lineSeparator()
                        + "Maria | 18/10/2000 | 2.009,44 | Operador" + System.lineSeparator(),
                logger.mensagem
        );
    }

    @Test
    void cadastrandoInvalido() {
        controlador.cadastrarFuncionario(null);
        assertEquals(0, repositorio.listar().size());
        assertEquals("ERRO: Entrada não pode ser nula" + System.lineSeparator(), logger.mensagem);
    }

    @Test
    void excluindoValido() {
        controlador.cadastrarFuncionario(maria);
        logger.mensagem = "";
        controlador.excluirFuncionario(new ExcluirFuncionario.Input("Maria"));
        assertEquals(0, repositorio.listar().size());
        assertEquals(
                "OK: Funcionário excluído" + System.lineSeparator()
                        + "Maria | 18/10/2000 | 2.009,44 | Operador" + System.lineSeparator(),
                logger.mensagem
        );
    }

    @Test
    void excluindoInvalido() {
        controlador.excluirFuncionario(null);
        assertEquals(0, repositorio.listar().size());
        assertEquals("ERRO: Entrada não pode ser nula" + System.lineSeparator(), logger.mensagem);
    }

    @Test
    void listandoValido() {
        controlador.cadastrarFuncionario(maria);
        controlador.cadastrarFuncionario(new CadastrarFuncionario.Input(
                "João",
                LocalDate.of(1990, 5, 12),
                new BigDecimal("2284.38"),
                "Operador"
        ));
        logger.mensagem = "";
        controlador.listarFuncionarios();
        assertEquals(
                "OK: Funcionários listados" + System.lineSeparator()
                        + "Maria | 18/10/2000 | 2.009,44 | Operador" + System.lineSeparator()
                        + "João | 12/05/1990 | 2.284,38 | Operador" + System.lineSeparator(),
                logger.mensagem
        );
    }

    @Test
    void listandoVazio() {
        controlador.listarFuncionarios();
        assertEquals("OK: Funcionários listados" + System.lineSeparator(), logger.mensagem);
    }

    @Test
    void listandoPorFuncaoValido() {
        controlador.cadastrarFuncionario(maria);
        controlador.cadastrarFuncionario(new CadastrarFuncionario.Input(
                "João",
                LocalDate.of(1990, 5, 12),
                new BigDecimal("2284.38"),
                "Operador"
        ));
        controlador.cadastrarFuncionario(new CadastrarFuncionario.Input(
                "Caio",
                LocalDate.of(1961, 5, 2),
                new BigDecimal("9836.14"),
                "Coordenador"
        ));
        logger.mensagem = "";
        controlador.listarFuncionariosPorFuncao();
        assertEquals(
                "OK: Funcionários listados por função" + System.lineSeparator()
                        + "Operador" + System.lineSeparator()
                        + "Maria | 18/10/2000 | 2.009,44 | Operador" + System.lineSeparator()
                        + "João | 12/05/1990 | 2.284,38 | Operador" + System.lineSeparator()
                        + "Coordenador" + System.lineSeparator()
                        + "Caio | 02/05/1961 | 9.836,14 | Coordenador" + System.lineSeparator(),
                logger.mensagem
        );
    }

    @Test
    void listandoPorFuncaoVazio() {
        controlador.listarFuncionariosPorFuncao();
        assertEquals("OK: Funcionários listados por função" + System.lineSeparator(), logger.mensagem);
    }

    @Test
    void listandoPorAniversarioValido() {
        controlador.cadastrarFuncionario(maria);
        controlador.cadastrarFuncionario(new CadastrarFuncionario.Input(
                "João",
                LocalDate.of(1990, 5, 12),
                new BigDecimal("2284.38"),
                "Operador"
        ));
        controlador.cadastrarFuncionario(new CadastrarFuncionario.Input(
                "Miguel",
                LocalDate.of(1988, 10, 14),
                new BigDecimal("19119.88"),
                "Diretor"
        ));
        logger.mensagem = "";
        controlador.listarFuncionariosPorAniversario(new ListarFuncionariosPorAniversario.Input(List.of(10, 12)));
        assertEquals(
                "OK: Funcionários listados por aniversário" + System.lineSeparator()
                        + "Maria | 18/10/2000 | 2.009,44 | Operador" + System.lineSeparator()
                        + "Miguel | 14/10/1988 | 19.119,88 | Diretor" + System.lineSeparator(),
                logger.mensagem
        );
    }

    @Test
    void listandoPorAniversarioVazio() {
        controlador.listarFuncionariosPorAniversario(new ListarFuncionariosPorAniversario.Input(List.of(10, 12)));
        assertEquals("OK: Funcionários listados por aniversário" + System.lineSeparator(), logger.mensagem);
    }

    @Test
    void listandoPorAniversarioInvalido() {
        controlador.listarFuncionariosPorAniversario(null);
        assertEquals("ERRO: Entrada não pode ser nula" + System.lineSeparator(), logger.mensagem);
    }

    @Test
    void listandoMaisVelhoValido() {
        controlador.cadastrarFuncionario(maria);
        controlador.cadastrarFuncionario(new CadastrarFuncionario.Input(
                "Caio",
                LocalDate.of(1961, 5, 2),
                new BigDecimal("9836.14"),
                "Coordenador"
        ));
        logger.mensagem = "";
        controlador.listarFuncionarioMaisVelho();
        int idade = Period.between(LocalDate.of(1961, 5, 2), LocalDate.now()).getYears();
        assertEquals(
                "OK: Funcionário mais velho listado" + System.lineSeparator()
                        + "Caio | " + idade + System.lineSeparator(),
                logger.mensagem
        );
    }

    @Test
    void listandoMaisVelhoVazio() {
        controlador.listarFuncionarioMaisVelho();
        assertEquals("OK: Funcionário mais velho listado" + System.lineSeparator(), logger.mensagem);
    }

    @Test
    void listandoQuantidadeSalariosMinimosValido() {
        controlador.cadastrarFuncionario(maria);
        controlador.cadastrarFuncionario(new CadastrarFuncionario.Input(
                "João",
                LocalDate.of(1990, 5, 12),
                new BigDecimal("2284.38"),
                "Operador"
        ));
        logger.mensagem = "";
        controlador.listarQuantidadeSalariosMinimos();
        assertEquals(
                "OK: Quantidade de salários mínimos listada" + System.lineSeparator()
                        + "Maria | 1,66" + System.lineSeparator()
                        + "João | 1,88" + System.lineSeparator(),
                logger.mensagem
        );
    }

    @Test
    void listandoQuantidadeSalariosMinimosVazio() {
        controlador.listarQuantidadeSalariosMinimos();
        assertEquals("OK: Quantidade de salários mínimos listada" + System.lineSeparator(), logger.mensagem);
    }

    @Test
    void listandoOrdenadoPorNome() {
        controlador.cadastrarFuncionario(maria);
        controlador.cadastrarFuncionario(new CadastrarFuncionario.Input(
                "João",
                LocalDate.of(1990, 5, 12),
                new BigDecimal("2284.38"),
                "Operador"
        ));
        controlador.cadastrarFuncionario(new CadastrarFuncionario.Input(
                "Caio",
                LocalDate.of(1961, 5, 2),
                new BigDecimal("9836.14"),
                "Coordenador"
        ));
        logger.mensagem = "";
        controlador.listarFuncionarios(new Ordenacao(Campo.NOME, Direcao.CRESCENTE));
        assertEquals(
                "OK: Funcionários listados" + System.lineSeparator()
                        + "Caio | 02/05/1961 | 9.836,14 | Coordenador" + System.lineSeparator()
                        + "João | 12/05/1990 | 2.284,38 | Operador" + System.lineSeparator()
                        + "Maria | 18/10/2000 | 2.009,44 | Operador" + System.lineSeparator(),
                logger.mensagem
        );
    }
}
