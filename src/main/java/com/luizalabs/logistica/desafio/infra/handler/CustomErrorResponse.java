package com.luizalabs.logistica.desafio.infra.handler;

public class CustomErrorResponse {
    int value;
    String message;
    String description;

    public CustomErrorResponse(int value, String message, String description) {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
