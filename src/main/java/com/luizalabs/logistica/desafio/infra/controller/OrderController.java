package com.luizalabs.logistica.desafio.infra.controller;

import com.luizalabs.logistica.desafio.application.usecase.GetOrderById;
import com.luizalabs.logistica.desafio.application.usecase.GetOrderByUserId;
import com.luizalabs.logistica.desafio.application.usecase.GetOrders;
import com.luizalabs.logistica.desafio.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final GetOrders getOrders;
    private final GetOrderById getOrderById;
    private final GetOrderByUserId getOrderByUserId;

    public OrderController(GetOrders getOrders, GetOrderById getOrderById, GetOrderByUserId getOrderByUserId) {
        this.getOrders = getOrders;
        this.getOrderById = getOrderById;
        this.getOrderByUserId = getOrderByUserId;
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PageableDefault Pageable pageable,
            @RequestParam(value = "startDate", required = false) Optional<LocalDate> startDate,
            @RequestParam(value = "endDate", required = false) Optional<LocalDate> endDate
    ) {
        Page<Order> orders = getOrders.execute(pageable, startDate, endDate);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = getOrderById.execute(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/orders/user/{id}")
    public ResponseEntity<Page<Order>> getOrderByUserId(@PathVariable Long id, @PageableDefault Pageable pageable) {
        Page<Order> orders = getOrderByUserId.execute(id, pageable);
        return ResponseEntity.ok(orders);
    }
}
