package com.luizalabs.logistica.desafio.infra.exception;

public class InvalidProductValueException extends RuntimeException {
    public InvalidProductValueException(String line) {
        super("Formato inválido para o valor do produto na linha: '" + line + "'");
    }
}
