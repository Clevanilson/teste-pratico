package br.com.iniflex.dominio;

import java.util.Objects;

public class Direcao {
    public static final Direcao CRESCENTE = new Direcao("CRESCENTE");
    public static final Direcao DECRESCENTE = new Direcao("DECRESCENTE");

    private final String valor;

    public Direcao(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Direção da ordenação não pode ser nula");
        }
        String normalizado = valor.trim().toUpperCase();
        if (!normalizado.equals("CRESCENTE") && !normalizado.equals("DECRESCENTE")) {
            throw new IllegalArgumentException("Direção deve ser CRESCENTE ou DECRESCENTE");
        }
        this.valor = normalizado;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof Direcao direcao)) {
            return false;
        }
        return valor.equals(direcao.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
