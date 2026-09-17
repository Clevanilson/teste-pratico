package br.com.iniflex.aplicacao.views;

import br.com.iniflex.aplicacao.servico.Logger;

import java.util.List;

public class TabelaView {
    private final Logger logger;

    public TabelaView(Logger logger) {
        this.logger = logger;
    }

    public void exibir(List<String> cabecalhos, List<List<String>> linhas) {
        imprimir(montar(cabecalhos, linhas));
    }

    public void exibir(String titulo, List<String> cabecalhos, List<List<String>> linhas) {
        imprimir(titulo + System.lineSeparator() + montar(cabecalhos, linhas));
    }

    private void imprimir(String conteudo) {
        if (conteudo.isBlank()) {
            return;
        }
        logger.log("%s", conteudo);
    }

    private String montar(List<String> cabecalhos, List<List<String>> linhas) {
        if (linhas.isEmpty()) {
            return "";
        }
        int[] larguras = calcularLarguraDasColunas(cabecalhos, linhas);
        String tabela = formatarLinha(cabecalhos, larguras) + System.lineSeparator();
        tabela += formatarLinhas(linhas, larguras);
        tabela += formatarFechamento(formatarLinha(cabecalhos, larguras).length());
        return tabela;
    }

    private int[] calcularLarguraDasColunas(List<String> cabecalhos, List<List<String>> linhas) {
        int[] larguras = new int[cabecalhos.size()];
        atualizarLarguras(larguras, cabecalhos);
        for (List<String> linha : linhas) {
            atualizarLarguras(larguras, linha);
        }
        return larguras;
    }

    private void atualizarLarguras(int[] larguras, List<String> celulas) {
        for (int i = 0; i < larguras.length; i++) {
            larguras[i] = Math.max(larguras[i], celulas.get(i).length());
        }
    }

    private String formatarLinhas(List<List<String>> linhas, int[] larguras) {
        String resultado = "";
        for (List<String> linha : linhas) {
            resultado += formatarLinha(linha, larguras) + System.lineSeparator();
        }
        return resultado;
    }

    private String formatarFechamento(int largura) {
        return "-".repeat(largura) + System.lineSeparator();
    }

    private String formatarLinha(List<String> celulas, int[] larguras) {
        String linha = "| ";
        for (int i = 0; i < larguras.length; i++) {
            if (i > 0) {
                linha += " | ";
            }
            linha += alinharAEsquerda(celulas.get(i), larguras[i]);
        }
        return linha + " |";
    }

    private String alinharAEsquerda(String valor, int largura) {
        return String.format("%-" + largura + "s", valor);
    }
}
