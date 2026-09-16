package br.com.iniflex.aplicacao.repositorio;

import br.com.iniflex.dominio.Funcionario;

import java.util.List;
import java.util.Map;

public interface FuncionarioRepositorio {
    void salvar(Funcionario funcionario);
    Funcionario remover(String nome);
    List<Funcionario> listar();
    Map<String, List<Funcionario>> listarPorFuncao();
    List<Funcionario> listarPorMesesAniversario(List<Integer> meses);
}
