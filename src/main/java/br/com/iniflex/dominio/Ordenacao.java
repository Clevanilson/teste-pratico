package br.com.iniflex.dominio;

public class Ordenacao {
    private Campo campo;
    private Direcao direcao;

    public Ordenacao(Campo campo, Direcao direcao) {
        setCampo(campo);
        setDirecao(direcao);
    }

    public Campo getCampo() {
        return campo;
    }

    public void setCampo(Campo campo) {
        if (campo == null) {
            throw new IllegalArgumentException("Campo da ordenação não pode ser nulo");
        }
        this.campo = campo;
    }

    public Direcao getDirecao() {
        return direcao;
    }

    public void setDirecao(Direcao direcao) {
        if (direcao == null) {
            throw new IllegalArgumentException("Direção da ordenação não pode ser nula");
        }
        this.direcao = direcao;
    }
}
