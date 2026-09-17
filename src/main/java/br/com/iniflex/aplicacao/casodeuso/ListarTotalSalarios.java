package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.aplicacao.repositorio.FuncionarioRepositorio;
import br.com.iniflex.dominio.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ListarTotalSalarios {
    private final FuncionarioRepositorio funcionarioRepositorio;

    public ListarTotalSalarios(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    public Output executar() {
        BigDecimal total = funcionarioRepositorio.listar().stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        return new Output(total);
    }

    public record Output(BigDecimal total) {
    }
}
