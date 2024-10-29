package com.luizalabs.logistica.desafio.infra.exception;

public class InvalidOrderIdException extends RuntimeException {
    public InvalidOrderIdException(String line) {
        super("Formato inválido para o ID do pedido na linha: '" + line + "'");
    }
}
