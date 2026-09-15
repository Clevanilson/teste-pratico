package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.aplicacao.repositorio.FuncionarioRepositorio;
import br.com.iniflex.dominio.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CadastrarFuncionario {
    private final FuncionarioRepositorio funcionarioRepositorio;

    public CadastrarFuncionario(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    public Output executar(Input input) {
        if (input == null) {
            throw new IllegalArgumentException("Entrada não pode ser nula");
        }
        Funcionario funcionario = new Funcionario(
                input.nome(),
                input.dataNascimento(),
                input.salario(),
                input.funcao()
        );
        funcionarioRepositorio.salvar(funcionario);
        return new Output(
                funcionario.getNome(),
                funcionario.getDataNascimento(),
                funcionario.getSalario(),
                funcionario.getFuncao()
        );
    }

    public record Input(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
    }

    public record Output(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
    }
}
