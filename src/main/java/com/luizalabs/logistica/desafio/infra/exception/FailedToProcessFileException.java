package com.luizalabs.logistica.desafio.infra.exception;

public class FailedToProcessFileException extends RuntimeException {
    public FailedToProcessFileException(String errorMessage) {
        super("Erro ao processar arquivo: '" + errorMessage + "'");
    }
}
