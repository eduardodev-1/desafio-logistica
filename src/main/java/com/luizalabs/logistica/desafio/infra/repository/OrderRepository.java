package com.luizalabs.logistica.desafio.infra.repository;

import com.luizalabs.logistica.desafio.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Order> findByDateAfter(LocalDate startDate, Pageable pageable);

    Page<Order> findByDateBefore(LocalDate endDate, Pageable pageable);

    Page<Order> findByUserId(Long id, Pageable pageable);
}
