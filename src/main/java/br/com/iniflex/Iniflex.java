package br.com.iniflex;

import br.com.iniflex.aplicacao.casodeuso.CadastrarFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ExcluirFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarioMaisVelho;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarios;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorAniversario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorFuncao;
import br.com.iniflex.aplicacao.controladores.FuncionarioControlador;
import br.com.iniflex.aplicacao.repositorio.FuncionarioRepositorio;
import br.com.iniflex.aplicacao.views.FuncionarioView;
import br.com.iniflex.dominio.Campo;
import br.com.iniflex.dominio.Direcao;
import br.com.iniflex.dominio.Ordenacao;
import br.com.iniflex.infra.repositorio.FuncionarioMemoriaRepositorio;
import br.com.iniflex.infra.servico.PrintfLogger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Iniflex {
    private final FuncionarioRepositorio funcionarioRepositorio;
    private final FuncionarioControlador funcionarioControlador;

    public Iniflex() {
        this.funcionarioRepositorio = new FuncionarioMemoriaRepositorio();
        this.funcionarioControlador = new FuncionarioControlador(
                new CadastrarFuncionario(funcionarioRepositorio),
                new ExcluirFuncionario(funcionarioRepositorio),
                new ListarFuncionarios(funcionarioRepositorio),
                new ListarFuncionariosPorFuncao(funcionarioRepositorio),
                new ListarFuncionariosPorAniversario(funcionarioRepositorio),
                new ListarFuncionarioMaisVelho(funcionarioRepositorio),
                new FuncionarioView(new PrintfLogger())
        );
    }

    public void cadastrarFuncionarios() {
        CadastrarFuncionario.Input[] funcionarios = {
                new CadastrarFuncionario.Input("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new CadastrarFuncionario.Input("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new CadastrarFuncionario.Input("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                new CadastrarFuncionario.Input("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new CadastrarFuncionario.Input("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                new CadastrarFuncionario.Input("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                new CadastrarFuncionario.Input("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                new CadastrarFuncionario.Input("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                new CadastrarFuncionario.Input("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                new CadastrarFuncionario.Input("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        };
        for (CadastrarFuncionario.Input funcionario : funcionarios) {
            funcionarioControlador.cadastrarFuncionario(funcionario);
        }
    }

    public void excluirFuncionarios() {
        ExcluirFuncionario.Input[] funcionarios = {
                new ExcluirFuncionario.Input("João")
        };
        for (ExcluirFuncionario.Input funcionario : funcionarios) {
            funcionarioControlador.excluirFuncionario(funcionario);
        }
    }

    public void listarFuncionarios() {
        funcionarioControlador.listarFuncionarios();
    }

    public void listarFuncionarios(Ordenacao ordenacao) {
        funcionarioControlador.listarFuncionarios(ordenacao);
    }

    public void listarFuncionariosPorFuncao() {
        funcionarioControlador.listarFuncionariosPorFuncao();
    }

    public void listarFuncionariosPorAniversario() {
        funcionarioControlador.listarFuncionariosPorAniversario(
                new ListarFuncionariosPorAniversario.Input(List.of(10, 12))
        );
    }

    public void listarFuncionarioMaisVelho() {
        funcionarioControlador.listarFuncionarioMaisVelho();
    }

    public String mensagem() {
        return "Hello World";
    }

    public static void main(String[] args) {
        Iniflex iniflex = new Iniflex();
        iniflex.cadastrarFuncionarios();
        iniflex.excluirFuncionarios();
        iniflex.listarFuncionarios();
        iniflex.listarFuncionariosPorFuncao();
        iniflex.listarFuncionariosPorAniversario();
        iniflex.listarFuncionarioMaisVelho();
        iniflex.listarFuncionarios(new Ordenacao(Campo.NOME, Direcao.CRESCENTE));
    }
}
