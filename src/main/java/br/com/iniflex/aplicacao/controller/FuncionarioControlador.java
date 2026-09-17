package br.com.iniflex.aplicacao.controller;

import br.com.iniflex.aplicacao.casodeuso.AumentarSalarioFuncionario;
import br.com.iniflex.aplicacao.casodeuso.CadastrarFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ExcluirFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarioMaisVelho;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarios;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorAniversario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorFuncao;
import br.com.iniflex.aplicacao.casodeuso.ListarQuantidadeSalariosMinimos;
import br.com.iniflex.aplicacao.casodeuso.ListarTotalSalarios;
import br.com.iniflex.aplicacao.view.FuncionarioView;
import br.com.iniflex.dominio.Ordenacao;

public class FuncionarioControlador {
    private static final String ERRO_INESPERADO = "Erro inesperado";

    private final CadastrarFuncionario cadastrarFuncionario;
    private final ExcluirFuncionario excluirFuncionario;
    private final ListarFuncionarios listarFuncionarios;
    private final AumentarSalarioFuncionario aumentarSalarioFuncionario;
    private final ListarFuncionariosPorFuncao listarFuncionariosPorFuncao;
    private final ListarFuncionariosPorAniversario listarFuncionariosPorAniversario;
    private final ListarFuncionarioMaisVelho listarFuncionarioMaisVelho;
    private final ListarTotalSalarios listarTotalSalarios;
    private final ListarQuantidadeSalariosMinimos listarQuantidadeSalariosMinimos;
    private final FuncionarioView funcionarioView;

    public FuncionarioControlador(
            CadastrarFuncionario cadastrarFuncionario,
            ExcluirFuncionario excluirFuncionario,
            ListarFuncionarios listarFuncionarios,
            AumentarSalarioFuncionario aumentarSalarioFuncionario,
            ListarFuncionariosPorFuncao listarFuncionariosPorFuncao,
            ListarFuncionariosPorAniversario listarFuncionariosPorAniversario,
            ListarFuncionarioMaisVelho listarFuncionarioMaisVelho,
            ListarTotalSalarios listarTotalSalarios,
            ListarQuantidadeSalariosMinimos listarQuantidadeSalariosMinimos,
            FuncionarioView funcionarioView
    ) {
        this.cadastrarFuncionario = cadastrarFuncionario;
        this.excluirFuncionario = excluirFuncionario;
        this.listarFuncionarios = listarFuncionarios;
        this.aumentarSalarioFuncionario = aumentarSalarioFuncionario;
        this.listarFuncionariosPorFuncao = listarFuncionariosPorFuncao;
        this.listarFuncionariosPorAniversario = listarFuncionariosPorAniversario;
        this.listarFuncionarioMaisVelho = listarFuncionarioMaisVelho;
        this.listarTotalSalarios = listarTotalSalarios;
        this.listarQuantidadeSalariosMinimos = listarQuantidadeSalariosMinimos;
        this.funcionarioView = funcionarioView;
    }

    public void cadastrarFuncionario(CadastrarFuncionario.Input input) {
        executar("3.1", () -> {
            CadastrarFuncionario.Output output = cadastrarFuncionario.executar(input);
            funcionarioView.toast(true, mensagemEtapa("3.1", "Funcionário cadastrado"));
            funcionarioView.exibir(output);
        });
    }

    public void excluirFuncionario(ExcluirFuncionario.Input input) {
        executar("3.2", () -> {
            ExcluirFuncionario.Output output = excluirFuncionario.executar(input);
            funcionarioView.toast(true, mensagemEtapa("3.2", "Funcionário excluído"));
            funcionarioView.exibir(output);
        });
    }

    public void listarFuncionarios() {
        listarFuncionarios("3.3", null);
    }

    public void listarFuncionarios(Ordenacao ordenacao) {
        listarFuncionarios("3.10", ordenacao);
    }

    public void aumentarSalarioFuncionario(AumentarSalarioFuncionario.Input input) {
        executar("3.4", () -> {
            AumentarSalarioFuncionario.Output output = aumentarSalarioFuncionario.executar(input);
            funcionarioView.toast(true, mensagemEtapa("3.4", "Salário de funcionários aumentado"));
            funcionarioView.exibir(output);
        });
    }

    public void listarFuncionariosPorFuncao() {
        executar("3.5", () -> {
            ListarFuncionariosPorFuncao.Output output = listarFuncionariosPorFuncao.executar();
            funcionarioView.toast(true, mensagemEtapa("3.5", "Funcionários agrupados por função"));
            funcionarioView.toast(true, mensagemEtapa("3.6", "Funcionários listados por função"));
            funcionarioView.exibir(output);
        });
    }

    public void listarFuncionariosPorAniversario(ListarFuncionariosPorAniversario.Input input) {
        executar("3.8", () -> {
            ListarFuncionariosPorAniversario.Output output = listarFuncionariosPorAniversario.executar(input);
            funcionarioView.toast(true, mensagemEtapa("3.8", "Funcionários listados por aniversário"));
            funcionarioView.exibir(output);
        });
    }

    public void listarFuncionarioMaisVelho() {
        executar("3.9", () -> {
            ListarFuncionarioMaisVelho.Output output = listarFuncionarioMaisVelho.executar();
            funcionarioView.toast(true, mensagemEtapa("3.9", "Funcionário mais velho listado"));
            funcionarioView.exibir(output);
        });
    }

    public void listarTotalSalarios() {
        executar("3.11", () -> {
            ListarTotalSalarios.Output output = listarTotalSalarios.executar();
            funcionarioView.toast(true, mensagemEtapa("3.11", "Total de salários listado"));
            funcionarioView.exibir(output);
        });
    }

    public void listarQuantidadeSalariosMinimos() {
        executar("3.12", () -> {
            ListarQuantidadeSalariosMinimos.Output output = listarQuantidadeSalariosMinimos.executar();
            funcionarioView.toast(true, mensagemEtapa("3.12", "Quantidade de salários mínimos listada"));
            funcionarioView.exibir(output);
        });
    }

    private void listarFuncionarios(String etapa, Ordenacao ordenacao) {
        executar(etapa, () -> {
            ListarFuncionarios.Output output = listarFuncionarios.executar(ordenacao);
            funcionarioView.toast(true, mensagemEtapa(etapa, "Funcionários listados"));
            funcionarioView.exibir(output);
        });
    }

    private void executar(String etapa, Runnable acao) {
        try {
            acao.run();
        } catch (RuntimeException e) {
            funcionarioView.toast(false, mensagemEtapa(etapa, mensagemErro(e)));
        }
    }

    private String mensagemErro(RuntimeException e) {
        String mensagem = e.getMessage();
        return mensagem == null || mensagem.isBlank() ? ERRO_INESPERADO : mensagem;
    }

    private static String mensagemEtapa(String etapa, String mensagem) {
        return etapa + " - " + mensagem;
    }
}
