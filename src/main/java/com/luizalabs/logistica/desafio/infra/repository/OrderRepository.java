package com.luizalabs.logistica.desafio.infra.repository;

import com.luizalabs.logistica.desafio.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
