package com.luizalabs.logistica.desafio.infra.exception;

public class JsonConverterException extends RuntimeException {
    public JsonConverterException(String errorMessage) {
        super("Erro ao processar json: '" + errorMessage + "'");
    }
}
