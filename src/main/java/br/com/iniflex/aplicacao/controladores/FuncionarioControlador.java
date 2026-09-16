package br.com.iniflex.aplicacao.controladores;

import br.com.iniflex.aplicacao.casodeuso.CadastrarFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ExcluirFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarios;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorFuncao;
import br.com.iniflex.aplicacao.views.FuncionarioView;

public class FuncionarioControlador {
    private final CadastrarFuncionario cadastrarFuncionario;
    private final ExcluirFuncionario excluirFuncionario;
    private final ListarFuncionarios listarFuncionarios;
    private final ListarFuncionariosPorFuncao listarFuncionariosPorFuncao;
    private final FuncionarioView funcionarioView;

    public FuncionarioControlador(
            CadastrarFuncionario cadastrarFuncionario,
            ExcluirFuncionario excluirFuncionario,
            ListarFuncionarios listarFuncionarios,
            ListarFuncionariosPorFuncao listarFuncionariosPorFuncao,
            FuncionarioView funcionarioView
    ) {
        this.cadastrarFuncionario = cadastrarFuncionario;
        this.excluirFuncionario = excluirFuncionario;
        this.listarFuncionarios = listarFuncionarios;
        this.listarFuncionariosPorFuncao = listarFuncionariosPorFuncao;
        this.funcionarioView = funcionarioView;
    }

    public void cadastrarFuncionario(CadastrarFuncionario.Input input) {
        try {
            CadastrarFuncionario.Output output = cadastrarFuncionario.executar(input);
            funcionarioView.toast(true, "Funcionário cadastrado");
            funcionarioView.exibir(output);
        } catch (RuntimeException e) {
            String mensagem = e.getMessage() == null || e.getMessage().isBlank()
                    ? "Falha ao cadastrar funcionário"
                    : e.getMessage();
            funcionarioView.toast(false, mensagem);
        }
    }

    public void excluirFuncionario(ExcluirFuncionario.Input input) {
        try {
            ExcluirFuncionario.Output output = excluirFuncionario.executar(input);
            funcionarioView.toast(true, "Funcionário excluído");
            funcionarioView.exibir(output);
        } catch (RuntimeException e) {
            String mensagem = e.getMessage() == null || e.getMessage().isBlank()
                    ? "Falha ao excluir funcionário"
                    : e.getMessage();
            funcionarioView.toast(false, mensagem);
        }
    }

    public void listarFuncionarios() {
        try {
            ListarFuncionarios.Output output = listarFuncionarios.executar();
            funcionarioView.toast(true, "Funcionários listados");
            funcionarioView.exibir(output);
        } catch (RuntimeException e) {
            String mensagem = e.getMessage() == null || e.getMessage().isBlank()
                    ? "Falha ao listar funcionários"
                    : e.getMessage();
            funcionarioView.toast(false, mensagem);
        }
    }

    public void listarFuncionariosPorFuncao() {
        try {
            ListarFuncionariosPorFuncao.Output output = listarFuncionariosPorFuncao.executar();
            funcionarioView.toast(true, "Funcionários listados por função");
            funcionarioView.exibir(output);
        } catch (RuntimeException e) {
            String mensagem = e.getMessage() == null || e.getMessage().isBlank()
                    ? "Falha ao listar funcionários por função"
                    : e.getMessage();
            funcionarioView.toast(false, mensagem);
        }
    }
}
