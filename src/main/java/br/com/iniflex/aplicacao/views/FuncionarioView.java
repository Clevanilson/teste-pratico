package br.com.iniflex.aplicacao.views;

import br.com.iniflex.aplicacao.casodeuso.AumentarSalarioFuncionario;
import br.com.iniflex.aplicacao.casodeuso.CadastrarFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ExcluirFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarioMaisVelho;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarios;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorAniversario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorFuncao;
import br.com.iniflex.aplicacao.casodeuso.ListarQuantidadeSalariosMinimos;
import br.com.iniflex.aplicacao.servico.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FuncionarioView {
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final List<String> CABECALHO_FUNCIONARIO = List.of("Nome", "Data Nascimento", "Salário", "Função");
    private static final List<String> CABECALHO_IDADE = List.of("Nome", "Idade");
    private static final List<String> CABECALHO_SALARIOS_MINIMOS = List.of("Nome", "Salários Mínimos");

    private final Logger logger;
    private final TabelaView tabelaView;
    private final DecimalFormat formatoSalario;

    public FuncionarioView(Logger logger) {
        this.logger = logger;
        this.tabelaView = new TabelaView(logger);
        this.formatoSalario = criarFormatoSalario();
    }

    public void exibir(CadastrarFuncionario.Output funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }
        tabelaView.exibir(CABECALHO_FUNCIONARIO, List.of(linhaFuncionario(
                funcionario.nome(),
                funcionario.dataNascimento(),
                funcionario.salario(),
                funcionario.funcao()
        )));
    }

    public void exibir(ExcluirFuncionario.Output funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }
        tabelaView.exibir(CABECALHO_FUNCIONARIO, List.of(linhaFuncionario(
                funcionario.nome(),
                funcionario.dataNascimento(),
                funcionario.salario(),
                funcionario.funcao()
        )));
    }

    public void exibir(ListarFuncionarios.Output funcionarios) {
        if (funcionarios == null || funcionarios.funcionarios() == null) {
            throw new IllegalArgumentException("Funcionários não podem ser nulos");
        }
        List<List<String>> linhas = new ArrayList<>();
        for (ListarFuncionarios.Output.Funcionario funcionario : funcionarios.funcionarios()) {
            if (funcionario == null) {
                throw new IllegalArgumentException("Funcionário não pode ser nulo");
            }
            linhas.add(linhaFuncionario(
                    funcionario.nome(),
                    funcionario.dataNascimento(),
                    funcionario.salario(),
                    funcionario.funcao()
            ));
        }
        tabelaView.exibir(CABECALHO_FUNCIONARIO, linhas);
    }

    public void exibir(AumentarSalarioFuncionario.Output funcionarios) {
        if (funcionarios == null || funcionarios.funcionarios() == null) {
            throw new IllegalArgumentException("Funcionários não podem ser nulos");
        }
        List<List<String>> linhas = new ArrayList<>();
        for (AumentarSalarioFuncionario.Output.Funcionario funcionario : funcionarios.funcionarios()) {
            if (funcionario == null) {
                throw new IllegalArgumentException("Funcionário não pode ser nulo");
            }
            linhas.add(linhaFuncionario(
                    funcionario.nome(),
                    funcionario.dataNascimento(),
                    funcionario.salario(),
                    funcionario.funcao()
            ));
        }
        tabelaView.exibir(CABECALHO_FUNCIONARIO, linhas);
    }

    public void exibir(ListarFuncionariosPorFuncao.Output funcionarios) {
        if (funcionarios == null || funcionarios.funcionariosPorFuncao() == null) {
            throw new IllegalArgumentException("Funcionários não podem ser nulos");
        }
        for (Map.Entry<String, List<ListarFuncionariosPorFuncao.Output.Funcionario>> grupo : funcionarios.funcionariosPorFuncao().entrySet()) {
            if (grupo.getKey() == null || grupo.getKey().isBlank() || grupo.getValue() == null) {
                throw new IllegalArgumentException("Funcionários não podem ser nulos");
            }
            List<List<String>> linhas = new ArrayList<>();
            for (ListarFuncionariosPorFuncao.Output.Funcionario funcionario : grupo.getValue()) {
                if (funcionario == null) {
                    throw new IllegalArgumentException("Funcionário não pode ser nulo");
                }
                linhas.add(linhaFuncionario(
                        funcionario.nome(),
                        funcionario.dataNascimento(),
                        funcionario.salario(),
                        funcionario.funcao()
                ));
            }
            tabelaView.exibir(grupo.getKey(), CABECALHO_FUNCIONARIO, linhas);
        }
    }

    public void exibir(ListarFuncionariosPorAniversario.Output funcionarios) {
        if (funcionarios == null || funcionarios.funcionarios() == null) {
            throw new IllegalArgumentException("Funcionários não podem ser nulos");
        }
        List<List<String>> linhas = new ArrayList<>();
        for (ListarFuncionariosPorAniversario.Output.Funcionario funcionario : funcionarios.funcionarios()) {
            if (funcionario == null) {
                throw new IllegalArgumentException("Funcionário não pode ser nulo");
            }
            linhas.add(linhaFuncionario(
                    funcionario.nome(),
                    funcionario.dataNascimento(),
                    funcionario.salario(),
                    funcionario.funcao()
            ));
        }
        tabelaView.exibir(CABECALHO_FUNCIONARIO, linhas);
    }

    public void exibir(ListarFuncionarioMaisVelho.Output funcionarios) {
        if (funcionarios == null || funcionarios.funcionarios() == null) {
            throw new IllegalArgumentException("Funcionários não podem ser nulos");
        }
        List<List<String>> linhas = new ArrayList<>();
        for (ListarFuncionarioMaisVelho.Output.Funcionario funcionario : funcionarios.funcionarios()) {
            if (funcionario == null) {
                throw new IllegalArgumentException("Funcionário não pode ser nulo");
            }
            linhas.add(List.of(funcionario.nome(), String.valueOf(funcionario.idade())));
        }
        tabelaView.exibir(CABECALHO_IDADE, linhas);
    }

    public void exibir(ListarQuantidadeSalariosMinimos.Output funcionarios) {
        if (funcionarios == null || funcionarios.funcionarios() == null) {
            throw new IllegalArgumentException("Funcionários não podem ser nulos");
        }
        List<List<String>> linhas = new ArrayList<>();
        for (ListarQuantidadeSalariosMinimos.Output.Funcionario funcionario : funcionarios.funcionarios()) {
            if (funcionario == null) {
                throw new IllegalArgumentException("Funcionário não pode ser nulo");
            }
            linhas.add(List.of(
                    funcionario.nome(),
                    formatoSalario.format(funcionario.quantidadeSalariosMinimos())
            ));
        }
        tabelaView.exibir(CABECALHO_SALARIOS_MINIMOS, linhas);
    }

    public void toast(boolean sucesso, String mensagem) {
        if (mensagem == null || mensagem.isBlank()) {
            throw new IllegalArgumentException("Mensagem não pode ser nula ou vazia");
        }
        logger.log("%s: %s%n", sucesso ? "OK" : "ERRO", mensagem.trim());
    }

    private List<String> linhaFuncionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        return List.of(
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
