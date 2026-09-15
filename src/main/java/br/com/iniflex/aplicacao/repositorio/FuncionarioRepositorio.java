package br.com.iniflex.aplicacao.repositorio;

import br.com.iniflex.dominio.Funcionario;

import java.util.List;

public interface FuncionarioRepositorio {
    void salvar(Funcionario funcionario);
    void remover(String nome);
    List<Funcionario> listar();
}
