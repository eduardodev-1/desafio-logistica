package com.luizalabs.logistica.desafio.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order", schema = "ecommerce")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long oldId;
    private BigDecimal total = BigDecimal.ZERO;

    private LocalDate date;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String productsJson;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String userJson;

    @Transient
    private List<Product> products = new ArrayList<>();

    @Transient
    private User user;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.userJson = convertUserToJson();
    }

    public void addProduct(Product product) {
        this.products.add(product);
        this.total = this.total.add(product.getValue());
        this.productsJson = convertProductsToJson();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", total=" + total +
                ", date=" + date +
                ", products=" + products +
                ", user=" + user +
                '}';
    }

    private String convertProductsToJson() {
        try {
            return objectMapper.writeValueAsString(this.products);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void convertJsonToProducts() {
        try {
            this.products = objectMapper.readValue(this.productsJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String convertUserToJson() {
        try {
            return objectMapper.writeValueAsString(this.user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void convertJsonToUser() {
        try {
            this.user = objectMapper.readValue(this.userJson, objectMapper.getTypeFactory().constructType(User.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @PostLoad
    private void onPostLoad() {
        convertJsonToProducts();
        convertJsonToUser();
    }
}
