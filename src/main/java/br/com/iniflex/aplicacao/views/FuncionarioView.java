package br.com.iniflex.aplicacao.views;

import br.com.iniflex.aplicacao.casodeuso.CadastrarFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ExcluirFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarios;
import br.com.iniflex.aplicacao.servico.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FuncionarioView {
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Logger logger;
    private final DecimalFormat formatoSalario;

    public FuncionarioView(Logger logger) {
        this.logger = logger;
        this.formatoSalario = criarFormatoSalario();
    }

    public void exibir(CadastrarFuncionario.Output funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }
        exibir(funcionario.nome(), funcionario.dataNascimento(), funcionario.salario(), funcionario.funcao());
    }

    public void exibir(ExcluirFuncionario.Output funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }
        exibir(funcionario.nome(), funcionario.dataNascimento(), funcionario.salario(), funcionario.funcao());
    }

    public void exibir(ListarFuncionarios.Output funcionarios) {
        if (funcionarios == null || funcionarios.funcionarios() == null) {
            throw new IllegalArgumentException("Funcionários não podem ser nulos");
        }
        for (ListarFuncionarios.Output.Funcionario funcionario : funcionarios.funcionarios()) {
            if (funcionario == null) {
                throw new IllegalArgumentException("Funcionário não pode ser nulo");
            }
            exibir(funcionario.nome(), funcionario.dataNascimento(), funcionario.salario(), funcionario.funcao());
        }
    }

    public void toast(boolean sucesso, String mensagem) {
        if (mensagem == null || mensagem.isBlank()) {
            throw new IllegalArgumentException("Mensagem não pode ser nula ou vazia");
        }
        logger.log("%s: %s%n", sucesso ? "OK" : "ERRO", mensagem.trim());
    }

    private void exibir(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        logger.log(
                "%s | %s | %s | %s%n",
                nome,
                dataNascimento.format(FORMATO_DATA),
                formatoSalario.format(salario),
                funcao
        );
    }

    private static DecimalFormat criarFormatoSalario() {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols(Locale.forLanguageTag("pt-BR"));
        simbolos.setGroupingSeparator('.');
        simbolos.setDecimalSeparator(',');
        DecimalFormat formato = new DecimalFormat("#,##0.00", simbolos);
        formato.setGroupingUsed(true);
        return formato;
    }
}
