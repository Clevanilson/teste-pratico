package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.aplicacao.repositorio.FuncionarioRepositorio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ListarFuncionariosPorAniversario {
    private final FuncionarioRepositorio funcionarioRepositorio;

    public ListarFuncionariosPorAniversario(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    public Output executar(Input input) {
        if (input == null) {
            throw new IllegalArgumentException("Entrada não pode ser nula");
        }
        validarMeses(input.meses());
        return new Output(
                funcionarioRepositorio.listar().stream()
                        .filter(funcionario -> input.meses().contains(funcionario.getDataNascimento().getMonthValue()))
                        .map(funcionario -> new Output.Funcionario(
                                funcionario.getNome(),
                                funcionario.getDataNascimento(),
                                funcionario.getSalario(),
                                funcionario.getFuncao()
                        ))
                        .toList()
        );
    }

    private void validarMeses(List<Integer> meses) {
        if (meses == null || meses.isEmpty()) {
            throw new IllegalArgumentException("Meses não podem ser nulos ou vazios");
        }
        for (Integer mes : meses) {
            if (mes == null || mes < 1 || mes > 12) {
                throw new IllegalArgumentException("Mês deve estar entre 1 e 12");
            }
        }
    }

    public record Input(List<Integer> meses) {
    }

    public record Output(List<Funcionario> funcionarios) {
        public record Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        }
    }
}
