package br.com.iniflex.aplicacao.view;

import br.com.iniflex.aplicacao.casodeuso.AumentarSalarioFuncionario;
import br.com.iniflex.aplicacao.casodeuso.CadastrarFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ExcluirFuncionario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarioMaisVelho;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionarios;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorAniversario;
import br.com.iniflex.aplicacao.casodeuso.ListarFuncionariosPorFuncao;
import br.com.iniflex.aplicacao.casodeuso.ListarQuantidadeSalariosMinimos;
import br.com.iniflex.aplicacao.casodeuso.ListarTotalSalarios;
import br.com.iniflex.infra.servico.LoggerFake;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioViewFake extends FuncionarioView {
    private final List<Chamada> chamadas = new ArrayList<>();

    public FuncionarioViewFake() {
        super(new LoggerFake());
    }

    public record Chamada(String metodo, List<Object> argumentos) {
        public Chamada(String metodo, Object... argumentos) {
            this(metodo, argumentos == null ? List.of() : List.of(argumentos));
        }
    }

    public List<Chamada> chamadas() {
        return List.copyOf(chamadas);
    }

    public boolean chamado(String metodo, Object... argumentos) {
        return chamadas.contains(new Chamada(metodo, argumentos));
    }

    public void limpar() {
        chamadas.clear();
    }

    @Override
    public void toast(boolean sucesso, String mensagem) {
        registrar("toast", sucesso, mensagem);
    }

    @Override
    public void exibir(CadastrarFuncionario.Output funcionario) {
        registrar("exibir", funcionario);
    }

    @Override
    public void exibir(ExcluirFuncionario.Output funcionario) {
        registrar("exibir", funcionario);
    }

    @Override
    public void exibir(ListarFuncionarios.Output funcionarios) {
        registrar("exibir", funcionarios);
    }

    @Override
    public void exibir(AumentarSalarioFuncionario.Output funcionarios) {
        registrar("exibir", funcionarios);
    }

    @Override
    public void exibir(ListarFuncionariosPorFuncao.Output funcionarios) {
        registrar("exibir", funcionarios);
    }

    @Override
    public void exibir(ListarFuncionariosPorAniversario.Output funcionarios) {
        registrar("exibir", funcionarios);
    }

    @Override
    public void exibir(ListarFuncionarioMaisVelho.Output funcionarios) {
        registrar("exibir", funcionarios);
    }

    @Override
    public void exibir(ListarQuantidadeSalariosMinimos.Output funcionarios) {
        registrar("exibir", funcionarios);
    }

    @Override
    public void exibir(ListarTotalSalarios.Output total) {
        registrar("exibir", total);
    }

    private void registrar(String metodo, Object... argumentos) {
        chamadas.add(new Chamada(metodo, argumentos));
    }
}
