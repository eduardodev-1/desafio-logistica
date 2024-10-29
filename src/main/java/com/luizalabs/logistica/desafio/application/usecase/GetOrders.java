package com.luizalabs.logistica.desafio.application.usecase;

import com.luizalabs.logistica.desafio.domain.entity.Order;
import com.luizalabs.logistica.desafio.infra.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class GetOrders {
    private final OrderRepository orderRepository;

    public GetOrders(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<Order> execute(Pageable pageable, Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
        if (startDate.isPresent() && endDate.isPresent()) {
            return orderRepository.findByDateBetween(startDate.get(), endDate.get(), pageable);
        } else if (startDate.isPresent()) {
            return orderRepository.findByDateAfter(startDate.get(), pageable);
        } else if (endDate.isPresent()) {
            return orderRepository.findByDateBefore(endDate.get(), pageable);
        } else {
            return orderRepository.findAll(pageable);
        }
    }
}
