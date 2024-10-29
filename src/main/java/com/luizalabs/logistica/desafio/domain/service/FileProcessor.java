package com.luizalabs.logistica.desafio.domain.service;

import com.luizalabs.logistica.desafio.domain.entity.*;
import com.luizalabs.logistica.desafio.infra.exception.EmptyFileException;
import com.luizalabs.logistica.desafio.infra.exception.FailedToProcessFileException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FileProcessor {

    public FileProcessorResult processFile(MultipartFile file) {
        Map<Long, Order> orders = new ConcurrentHashMap<>();
        Map<Long, User> users = new ConcurrentHashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            if (reader.readLine() == null) {
                throw new EmptyFileException(file.getOriginalFilename());
            }
            reader.lines().parallel().forEach(line -> {
                FileData data = FileData.FromLine(line);
                User user = getOrCreateNewUser(users, data);
                Order order = getOrCreateNewOrder(orders, user, data);
                order.addProduct(new Product(data.getProductId(), data.getProductValue()));
            });
        } catch (IOException e) {
            throw new FailedToProcessFileException(e.getMessage());
        }
        return new FileProcessorResult(List.copyOf(orders.values()), List.copyOf(users.values()));
    }

    private User getOrCreateNewUser(Map<Long, User> users, FileData data) {
        return users.computeIfAbsent(data.getUserId(), id ->
                new User(data.getUserId(), data.getUserName())
        );
    }

    private Order getOrCreateNewOrder(Map<Long, Order> orders, User user, FileData data) {
        return orders.computeIfAbsent(data.getOrderId(), id ->
                new Order(data.getOrderId(), data.getOrderDate(), user)
        );
    }
}
