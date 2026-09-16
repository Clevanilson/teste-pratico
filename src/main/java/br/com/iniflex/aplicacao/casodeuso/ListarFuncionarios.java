package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.aplicacao.repositorio.FuncionarioRepositorio;
import br.com.iniflex.dominio.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ListarFuncionarios {
    private final FuncionarioRepositorio funcionarioRepositorio;

    public ListarFuncionarios(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    public Output executar() {
        List<Funcionario> funcionarios = funcionarioRepositorio.listar();
        return new Output(
                funcionarios.stream()
                        .map(funcionario -> new Output.Funcionario(
                                funcionario.getNome(),
                                funcionario.getDataNascimento(),
                                funcionario.getSalario(),
                                funcionario.getFuncao()
                        ))
                        .toList()
        );
    }

    public record Output(List<Funcionario> funcionarios) {
        public record Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        }
    }
}
