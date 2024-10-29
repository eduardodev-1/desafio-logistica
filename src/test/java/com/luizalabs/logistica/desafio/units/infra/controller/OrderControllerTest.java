package com.luizalabs.logistica.desafio.units.infra.controller;

import com.luizalabs.logistica.desafio.application.usecase.GetOrderById;
import com.luizalabs.logistica.desafio.application.usecase.GetOrderByUserId;
import com.luizalabs.logistica.desafio.application.usecase.GetOrders;
import com.luizalabs.logistica.desafio.domain.entity.Order;
import com.luizalabs.logistica.desafio.domain.entity.User;
import com.luizalabs.logistica.desafio.infra.controller.OrderController;
import com.luizalabs.logistica.desafio.infra.exception.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private GetOrders getOrders;

    @Mock
    private GetOrderById getOrderById;

    @Mock
    private GetOrderByUserId getOrderByUserId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrdersSuccess() {
        Pageable pageable = Pageable.unpaged();
        Page<Order> ordersPage = createOrdersPageWithOneOrder();

        when(getOrders.execute(pageable, Optional.empty(), Optional.empty())).thenReturn(ordersPage);

        ResponseEntity<Page<Order>> response = orderController.getOrders(pageable, Optional.empty(), Optional.empty());

        assertResponseHasOrders(response);
        verify(getOrders, times(1)).execute(pageable, Optional.empty(), Optional.empty());
    }

    @Test
    void testGetOrdersEmptyList() {
        Pageable pageable = Pageable.unpaged();
        Page<Order> ordersPage = createEmptyOrdersPage();

        when(getOrders.execute(pageable, Optional.empty(), Optional.empty())).thenReturn(ordersPage);

        ResponseEntity<Page<Order>> response = orderController.getOrders(pageable, Optional.empty(), Optional.empty());

        assertResponseIsEmpty(response);
        verify(getOrders, times(1)).execute(pageable, Optional.empty(), Optional.empty());
    }

    @Test
    void testGetOrderByIdSuccess() {
        Long orderId = 1L;
        Order order = new Order(orderId, LocalDate.now(), new User(1L, "Test User"));
        when(getOrderById.execute(orderId)).thenReturn(order);

        ResponseEntity<Order> response = orderController.getOrderById(orderId);

        assertResponseHasOrder(response, order);
        verify(getOrderById, times(1)).execute(orderId);
    }

    @Test
    void testGetOrderByIdNotFound() {
        Long orderId = 1L;
        when(getOrderById.execute(orderId)).thenThrow(new OrderNotFoundException(1L));

        assertThrows(OrderNotFoundException.class, () -> orderController.getOrderById(orderId));
        verify(getOrderById, times(1)).execute(orderId);
    }

    @Test
    void testGetOrderByUserIdSuccess() {
        Long userId = 1L;
        Pageable pageable = Pageable.unpaged();
        Page<Order> ordersPage = createOrdersPageWithOneOrder();

        when(getOrderByUserId.execute(userId, pageable)).thenReturn(ordersPage);

        ResponseEntity<Page<Order>> response = orderController.getOrderByUserId(userId, pageable);

        assertResponseHasOrders(response);
        verify(getOrderByUserId, times(1)).execute(userId, pageable);
    }

    @Test
    void testGetOrderByUserIdEmptyList() {
        Long userId = 1L;
        Pageable pageable = Pageable.unpaged();
        Page<Order> ordersPage = createEmptyOrdersPage();

        when(getOrderByUserId.execute(userId, pageable)).thenReturn(ordersPage);

        ResponseEntity<Page<Order>> response = orderController.getOrderByUserId(userId, pageable);

        assertResponseIsEmpty(response);
        verify(getOrderByUserId, times(1)).execute(userId, pageable);
    }

    private Page<Order> createOrdersPageWithOneOrder() {
        Order order = new Order(1L, LocalDate.now(), new User(1L, "Test User"));
        return new PageImpl<>(Collections.singletonList(order));
    }

    private Page<Order> createEmptyOrdersPage() {
        return new PageImpl<>(Collections.emptyList());
    }

    private void assertResponseHasOrders(ResponseEntity<Page<Order>> response) {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getContent().isEmpty());
    }

    private void assertResponseIsEmpty(ResponseEntity<Page<Order>> response) {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getContent().isEmpty());
    }

    private void assertResponseHasOrder(ResponseEntity<Order> response, Order expectedOrder) {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedOrder, response.getBody());
    }
}
