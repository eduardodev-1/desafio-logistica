package com.luizalabs.logistica.desafio.infra.exception;

public class EmptyFileException  extends RuntimeException {
    public EmptyFileException(String errorMessage) {
        super("Arquivo vazio: '" + errorMessage + "'");
    }
}
