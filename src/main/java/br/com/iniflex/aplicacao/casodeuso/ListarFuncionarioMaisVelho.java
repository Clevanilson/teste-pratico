package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.aplicacao.repositorio.FuncionarioRepositorio;
import br.com.iniflex.dominio.Campo;
import br.com.iniflex.dominio.Consulta;
import br.com.iniflex.dominio.Direcao;
import br.com.iniflex.dominio.Ordenacao;

import java.util.List;

public class ListarFuncionarioMaisVelho {
    private final FuncionarioRepositorio funcionarioRepositorio;

    public ListarFuncionarioMaisVelho(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    public Output executar() {
        return new Output(
                funcionarioRepositorio.listar(new Consulta(new Ordenacao(Campo.IDADE, Direcao.DECRESCENTE), 1)).stream()
                        .map(funcionario -> new Output.Funcionario(
                                funcionario.getNome(),
                                funcionario.getIdade()
                        ))
                        .toList()
        );
    }

    public record Output(List<Funcionario> funcionarios) {
        public record Funcionario(String nome, int idade) {
        }
    }
}
