package com.luizalabs.logistica.desafio.domain.service;

import com.luizalabs.logistica.desafio.domain.entity.FileData;
import com.luizalabs.logistica.desafio.domain.entity.Order;
import com.luizalabs.logistica.desafio.domain.entity.Product;
import com.luizalabs.logistica.desafio.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileProcessor {

    public static FileData processFile(MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        Map<Long, User> userMap = new HashMap<>();
        Map<Long, Order> orderMap = new HashMap<>();

        while ((line = reader.readLine()) != null) {
            // Extrair os dados da linha com base nos índices fornecidos
            String userIdStr = line.substring(0, 10).replaceFirst("^0+(?!$)", "");
            String userName = line.substring(10, 55).trim();
            String orderIdStr = line.substring(55, 65).replaceFirst("^0+(?!$)", "");
            String prodIdStr = line.substring(65, 75).replaceFirst("^0+(?!$)", "");
            String valueStr = line.substring(75, 87).trim();
            String dateStr = line.substring(87, 95);
            Long userId = Long.parseLong(userIdStr);
            Long orderId = Long.parseLong(orderIdStr);
            Long productId = Long.parseLong(prodIdStr);
            BigDecimal value = new BigDecimal(valueStr);

            LocalDate date = LocalDate.parse(dateStr, formatter);

            // Criar ou obter Order e adicionar Product
            Order order = orderMap.computeIfAbsent(orderId, id -> {
                Order newOrder = new Order();
                newOrder.setId(orderId);
                newOrder.setDate(date);
                newOrder.setUser(new User(userId, userName));
                return newOrder;
            });
            order.addProduct(new Product(productId, value));
        }
        reader.close();
        return new FileData(new ArrayList<>(userMap.values()), new ArrayList<>(orderMap.values()));
    }
}
