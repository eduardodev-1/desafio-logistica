package com.luizalabs.logistica.desafio.infra.repository;

import com.luizalabs.logistica.desafio.domain.entity.FileData;
import com.luizalabs.logistica.desafio.domain.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileDataRepository {
    private final OrderRepository orderRepository;

    public FileDataRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(FileData fileData) {
        List<Order> orders = orderRepository.saveAll(fileData.getOrders());
    }
}
