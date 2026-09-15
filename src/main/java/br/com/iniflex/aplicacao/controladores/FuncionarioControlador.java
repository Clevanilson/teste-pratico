package br.com.iniflex.aplicacao.controladores;

import br.com.iniflex.aplicacao.casodeuso.CadastrarFuncionario;
import br.com.iniflex.aplicacao.views.FuncionarioView;

public class FuncionarioControlador {
    private final CadastrarFuncionario cadastrarFuncionario;
    private final FuncionarioView funcionarioView;

    public FuncionarioControlador(CadastrarFuncionario cadastrarFuncionario, FuncionarioView funcionarioView) {
        this.cadastrarFuncionario = cadastrarFuncionario;
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
}
