package br.com.iniflex.infra.servico;

import br.com.iniflex.aplicacao.servico.Logger;

public class LoggerFake implements Logger {
    public String mensagem = "";

    @Override
    public void log(String format, Object... args) {
        mensagem += String.format(format, args);
    }
}
