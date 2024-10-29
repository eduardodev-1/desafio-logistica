package com.luizalabs.logistica.desafio.infra.exception;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String line) {
        super("Formato inválido para o data do pedido na linha: '" + line + "'");
    }
}
