package com.luizalabs.logistica.desafio.infra.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
        super("Pedido não encontrado: " + orderId);
    }
}