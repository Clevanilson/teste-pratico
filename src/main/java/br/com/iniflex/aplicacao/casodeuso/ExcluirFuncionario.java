package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.aplicacao.repositorio.FuncionarioRepositorio;
import br.com.iniflex.dominio.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExcluirFuncionario {
    private final FuncionarioRepositorio funcionarioRepositorio;

    public ExcluirFuncionario(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    public Output executar(Input input) {
        if (input == null) {
            throw new IllegalArgumentException("Entrada não pode ser nula");
        }
        Funcionario funcionario = funcionarioRepositorio.remover(input.nome());
        return new Output(
                funcionario.getNome(),
                funcionario.getDataNascimento(),
                funcionario.getSalario(),
                funcionario.getFuncao()
        );
    }

    public record Input(String nome) {
    }

    public record Output(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
    }
}
