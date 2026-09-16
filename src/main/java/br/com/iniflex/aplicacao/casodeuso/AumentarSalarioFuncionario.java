package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.aplicacao.repositorio.FuncionarioRepositorio;
import br.com.iniflex.dominio.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AumentarSalarioFuncionario {
    private final FuncionarioRepositorio funcionarioRepositorio;

    public AumentarSalarioFuncionario(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    public Output executar(Input input) {
        if (input == null) {
            throw new IllegalArgumentException("Entrada não pode ser nula");
        }
        List<Funcionario> funcionarios = funcionarioRepositorio.listar();
        funcionarios.forEach(funcionario -> {
            funcionario.aumentarSalario(input.percentual());
            funcionarioRepositorio.salvar(funcionario);
        });
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

    public record Input(double percentual) {
    }

    public record Output(List<Funcionario> funcionarios) {
        public record Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        }
    }
}
