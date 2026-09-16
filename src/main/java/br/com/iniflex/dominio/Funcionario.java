package br.com.iniflex.dominio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Funcionario extends Pessoa {
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        setSalario(salario);
        setFuncao(funcao);
    }

    public BigDecimal getSalario() {
        return salario;
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

    public BigDecimal getQuantidadeSalariosMinimos() {
        return salario.divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);
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
