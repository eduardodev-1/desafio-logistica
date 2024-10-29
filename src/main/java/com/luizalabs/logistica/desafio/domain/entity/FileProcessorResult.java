package com.luizalabs.logistica.desafio.domain.entity;

import java.util.List;

public record FileProcessorResult(List<Order> orders, List<User> users) {
}
