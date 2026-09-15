package br.com.iniflex.infra.servico;

import br.com.iniflex.aplicacao.servico.Logger;

public class PrintfLogger implements Logger {
    @Override
    public void log(String format, Object... args) {
        System.out.printf(format, args);
    }
}
