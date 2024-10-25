package com.luizalabs.logistica.desafio.domain.entity;

import java.util.List;

public class FileData {
    private List<User> users;
    private List<Order> orders;

    public FileData(List<User> users, List<Order> orders) {
        this.users = users;
        this.orders = orders;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "users=" + users +
                ", orders=" + orders +
                '}';
    }
}
