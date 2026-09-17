package br.com.iniflex.aplicacao.controladores;

import br.com.iniflex.aplicacao.casodeuso.AumentarSalarioFuncionario;
import br.com.iniflex.aplicacao.casodeuso.CadastrarFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ExcluirFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarioMaisVelho;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarios;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorAniversario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorFuncao;
import br.com.iniflex.aplicacao.casodeuso.ListarQuantidadeSalariosMinimos;
import br.com.iniflex.aplicacao.views.FuncionarioView;
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
        this.listarQuantidadeSalariosMinimos = listarQuantidadeSalariosMinimos;
        this.funcionarioView = funcionarioView;
    }

    public void cadastrarFuncionario(CadastrarFuncionario.Input input) {
        executar(() -> {
            CadastrarFuncionario.Output output = cadastrarFuncionario.executar(input);
            funcionarioView.toast(true, "Funcionário cadastrado");
            funcionarioView.exibir(output);
        });
    }

    public void excluirFuncionario(ExcluirFuncionario.Input input) {
        executar(() -> {
            ExcluirFuncionario.Output output = excluirFuncionario.executar(input);
            funcionarioView.toast(true, "Funcionário excluído");
            funcionarioView.exibir(output);
        });
    }

    public void listarFuncionarios() {
        listarFuncionarios(null);
    }

    public void listarFuncionarios(Ordenacao ordenacao) {
        executar(() -> {
            ListarFuncionarios.Output output = listarFuncionarios.executar(ordenacao);
            funcionarioView.toast(true, "Funcionários listados");
            funcionarioView.exibir(output);
        });
    }

    public void aumentarSalarioFuncionario(AumentarSalarioFuncionario.Input input) {
        executar(() -> {
            AumentarSalarioFuncionario.Output output = aumentarSalarioFuncionario.executar(input);
            funcionarioView.toast(true, "Salário de funcionários aumentado");
            funcionarioView.exibir(output);
        });
    }

    public void listarFuncionariosPorFuncao() {
        executar(() -> {
            ListarFuncionariosPorFuncao.Output output = listarFuncionariosPorFuncao.executar();
            funcionarioView.toast(true, "Funcionários listados por função");
            funcionarioView.exibir(output);
        });
    }

    public void listarFuncionariosPorAniversario(ListarFuncionariosPorAniversario.Input input) {
        executar(() -> {
            ListarFuncionariosPorAniversario.Output output = listarFuncionariosPorAniversario.executar(input);
            funcionarioView.toast(true, "Funcionários listados por aniversário");
            funcionarioView.exibir(output);
        });
    }

    public void listarFuncionarioMaisVelho() {
        executar(() -> {
            ListarFuncionarioMaisVelho.Output output = listarFuncionarioMaisVelho.executar();
            funcionarioView.toast(true, "Funcionário mais velho listado");
            funcionarioView.exibir(output);
        });
    }

    public void listarQuantidadeSalariosMinimos() {
        executar(() -> {
            ListarQuantidadeSalariosMinimos.Output output = listarQuantidadeSalariosMinimos.executar();
            funcionarioView.toast(true, "Quantidade de salários mínimos listada");
            funcionarioView.exibir(output);
        });
    }

    private void executar(Runnable acao) {
        try {
            acao.run();
        } catch (RuntimeException e) {
            funcionarioView.toast(false, mensagemErro(e));
        }
    }

    private String mensagemErro(RuntimeException e) {
        String mensagem = e.getMessage();
        return mensagem == null || mensagem.isBlank() ? ERRO_INESPERADO : mensagem;
    }
}
