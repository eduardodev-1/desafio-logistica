package com.luizalabs.logistica.desafio.application.usecase;

import com.luizalabs.logistica.desafio.domain.entity.Order;
import com.luizalabs.logistica.desafio.infra.exception.OrderNotFoundException;
import com.luizalabs.logistica.desafio.infra.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetOrderByUserId {
    private final OrderRepository orderRepository;

    public GetOrderByUserId(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<Order> execute(Long id, Pageable pageable) {
        return orderRepository.findByUserId(id, pageable);
    }
}
