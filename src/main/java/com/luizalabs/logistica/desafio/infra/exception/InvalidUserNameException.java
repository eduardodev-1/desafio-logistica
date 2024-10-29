package com.luizalabs.logistica.desafio.infra.exception;

public class InvalidUserNameException extends RuntimeException {
    public InvalidUserNameException(String line) {
        super("Nome de usuário em branco na linha: '" + line + "'");
    }
}
