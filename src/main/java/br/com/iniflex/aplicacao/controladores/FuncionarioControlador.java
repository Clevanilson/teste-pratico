package br.com.iniflex.aplicacao.controladores;

import br.com.iniflex.aplicacao.casodeuso.CadastrarFuncionario;

public class FuncionarioControlador {
    private final CadastrarFuncionario cadastrarFuncionario;

    public FuncionarioControlador(CadastrarFuncionario cadastrarFuncionario) {
        this.cadastrarFuncionario = cadastrarFuncionario;
    }

    public void cadastrarFuncionario(CadastrarFuncionario.Input input) {
        cadastrarFuncionario.executar(input);
    }
}
