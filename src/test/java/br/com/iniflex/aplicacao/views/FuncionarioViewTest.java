package br.com.iniflex.aplicacao.views;

import br.com.iniflex.aplicacao.casodeuso.CadastrarFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ExcluirFuncionario;
import br.com.iniflex.infra.servico.LoggerFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FuncionarioViewTest {
    private LoggerFake logger;
    private FuncionarioView view;
    private CadastrarFuncionario.Output maria;

    @BeforeEach
    void setUp() {
        logger = new LoggerFake();
        view = new FuncionarioView(logger);
        maria = new CadastrarFuncionario.Output("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
    }

    @Test
    void exibindoFuncionario() {
        view.exibir(maria);
        assertEquals("Maria | 18/10/2000 | 2.009,44 | Operador" + System.lineSeparator(), logger.mensagem);
    }

    @Test
    void exibindoNulo() {
        assertThrows(IllegalArgumentException.class, () -> view.exibir((CadastrarFuncionario.Output) null));
        assertThrows(IllegalArgumentException.class, () -> view.exibir((ExcluirFuncionario.Output) null));
    }

    @Test
    void exibindoFuncionarioExcluido() {
        view.exibir(new ExcluirFuncionario.Output("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        assertEquals("Maria | 18/10/2000 | 2.009,44 | Operador" + System.lineSeparator(), logger.mensagem);
    }

    @Test
    void toastSucesso() {
        view.toast(true, "Funcionário cadastrado");
        assertEquals("OK: Funcionário cadastrado" + System.lineSeparator(), logger.mensagem);
    }

    @Test
    void toastErro() {
        view.toast(false, "Falha ao cadastrar funcionário");
        assertEquals("ERRO: Falha ao cadastrar funcionário" + System.lineSeparator(), logger.mensagem);
    }

    @Test
    void toastMensagemInvalida() {
        assertThrows(IllegalArgumentException.class, () -> view.toast(true, null));
        assertThrows(IllegalArgumentException.class, () -> view.toast(true, ""));
        assertThrows(IllegalArgumentException.class, () -> view.toast(true, " "));
    }
}
