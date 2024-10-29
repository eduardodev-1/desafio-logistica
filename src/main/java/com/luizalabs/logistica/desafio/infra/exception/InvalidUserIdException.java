package com.luizalabs.logistica.desafio.infra.exception;

public class InvalidUserIdException extends RuntimeException {
    public InvalidUserIdException(String line) {
        super("Formato inválido para o ID do usuário na linha: '" + line + "'");
    }
}
