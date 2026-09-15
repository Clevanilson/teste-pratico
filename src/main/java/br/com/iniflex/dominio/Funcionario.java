package br.com.iniflex.dominio;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        setSalario(salario);
        setFuncao(funcao);
    }

    public String getSalario() {
        NumberFormat formatador = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        formatador.setMinimumFractionDigits(2);
        formatador.setMaximumFractionDigits(2);
        return formatador.format(salario);
    }

    public void setSalario(BigDecimal salario) {
        if (salario == null) {
            throw new IllegalArgumentException("Salário não pode ser nulo");
        }
        if (salario.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Salário não pode ser negativo");
        }
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        if (funcao == null || funcao.isBlank()) {
            throw new IllegalArgumentException("Função não pode ser nula ou vazia");
        }
        this.funcao = funcao.trim();
    }
}
