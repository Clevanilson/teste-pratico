package br.com.iniflex.aplicacao.casodeuso;

import br.com.iniflex.aplicacao.repositorio.FuncionarioRepositorio;
import br.com.iniflex.dominio.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ListarFuncionariosPorFuncao {
    private final FuncionarioRepositorio funcionarioRepositorio;

    public ListarFuncionariosPorFuncao(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    public Output executar() {
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarioRepositorio.listarPorFuncao();
        Map<String, List<Output.Funcionario>> agrupados = new LinkedHashMap<>();
        funcionariosPorFuncao.forEach((funcao, funcionarios) ->
                agrupados.put(
                        funcao,
                        funcionarios.stream()
                                .map(funcionario -> new Output.Funcionario(
                                        funcionario.getNome(),
                                        funcionario.getDataNascimento(),
                                        funcionario.getSalario(),
                                        funcionario.getFuncao()
                                ))
                                .toList()
                )
        );
        return new Output(agrupados);
    }

    public record Output(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        public record Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        }
    }
}
