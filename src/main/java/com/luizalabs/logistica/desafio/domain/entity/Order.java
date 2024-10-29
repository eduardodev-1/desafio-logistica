package com.luizalabs.logistica.desafio.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luizalabs.logistica.desafio.infra.exception.JsonConverterException;
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
    private Long id;
    private BigDecimal total = BigDecimal.ZERO;
    private LocalDate date;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @JsonIgnore
    private String productsJson;

    @Transient
    @JsonProperty
    private List<Product> products = new ArrayList<>();

    @ManyToOne
    private User user;

    public Order() {
    }

    public Order(Long id, LocalDate date, User user) {
        this.id = id;
        this.date = date;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getProductsJson() {
        return productsJson;
    }

    public void setProductsJson(String productsJson) {
        this.productsJson = productsJson;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        this.total = this.total.add(product.getValue());
        this.productsJson = JsonConverter.convertProductsToJson(products);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return id.equals(order.id);
    }

    @PostLoad
    private void onPostLoad() {
        this.products = JsonConverter.convertJsonToProducts(productsJson);
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

    private static class JsonConverter {
        private static final ObjectMapper objectMapper;

        static {
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
        }

        private static String convertProductsToJson(List<Product> products) {
            try {
                return objectMapper.writeValueAsString(products);
            } catch (JsonProcessingException e) {
                throw new JsonConverterException(e.getMessage());
            }
        }

        private static List<Product> convertJsonToProducts(String productsJson) {
            try {
                return objectMapper.readValue(productsJson, objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
            } catch (JsonProcessingException e) {
                throw new JsonConverterException(e.getMessage());
            }
        }

        private static String convertOrderToJson(Order order) {
            try {
                return objectMapper.writeValueAsString(order);
            } catch (JsonProcessingException e) {
                throw new JsonConverterException(e.getMessage());
            }
        }
    }
}
