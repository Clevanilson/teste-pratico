package br.com.iniflex.dominio;

public class Consulta {
    private Ordenacao ordenacao;
    private Integer limite;

    public Consulta(Ordenacao ordenacao, Integer limite) {
        setOrdenacao(ordenacao);
        setLimite(limite);
    }

    public Ordenacao getOrdenacao() {
        return ordenacao;
    }

    public void setOrdenacao(Ordenacao ordenacao) {
        this.ordenacao = ordenacao;
    }

    public Integer getLimite() {
        return limite;
    }

    public void setLimite(Integer limite) {
        if (limite != null && limite < 1) {
            throw new IllegalArgumentException("Limite deve ser maior que zero");
        }
        this.limite = limite;
    }
}
