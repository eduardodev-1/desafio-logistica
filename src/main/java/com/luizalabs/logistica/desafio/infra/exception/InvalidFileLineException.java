package com.luizalabs.logistica.desafio.infra.exception;

public class InvalidFileLineException extends RuntimeException {
    public InvalidFileLineException(String line) {
        super("Formato inválido da linha do arquivo: '" + line + "'");
    }
}
