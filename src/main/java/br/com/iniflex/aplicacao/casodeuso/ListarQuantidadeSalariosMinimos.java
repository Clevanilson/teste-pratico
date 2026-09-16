package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.aplicacao.repositorio.FuncionarioRepositorio;
import br.com.iniflex.dominio.Funcionario;

import java.math.BigDecimal;
import java.util.List;

public class ListarQuantidadeSalariosMinimos {
    private final FuncionarioRepositorio funcionarioRepositorio;

    public ListarQuantidadeSalariosMinimos(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    public Output executar() {
        List<Funcionario> funcionarios = funcionarioRepositorio.listar();
        return new Output(
                funcionarios.stream()
                        .map(funcionario -> new Output.Funcionario(
                                funcionario.getNome(),
                                funcionario.getQuantidadeSalariosMinimos()
                        ))
                        .toList()
        );
    }

    public record Output(List<Funcionario> funcionarios) {
        public record Funcionario(String nome, BigDecimal quantidadeSalariosMinimos) {
        }
    }
}
