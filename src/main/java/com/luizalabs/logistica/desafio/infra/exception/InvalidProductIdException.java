package com.luizalabs.logistica.desafio.infra.exception;

public class InvalidProductIdException extends RuntimeException {
    public InvalidProductIdException(String line) {
        super("Formato inválido para o ID do produto na linha: '" + line + "'");
    }
}
