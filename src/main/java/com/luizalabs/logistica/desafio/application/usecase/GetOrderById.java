package com.luizalabs.logistica.desafio.application.usecase;

import com.luizalabs.logistica.desafio.domain.entity.Order;
import com.luizalabs.logistica.desafio.infra.exception.OrderNotFoundException;
import com.luizalabs.logistica.desafio.infra.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class GetOrderById {
    private final OrderRepository orderRepository;

    public GetOrderById(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order execute(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
}
//teste