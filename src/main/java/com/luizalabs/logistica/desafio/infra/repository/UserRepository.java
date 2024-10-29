package com.luizalabs.logistica.desafio.infra.repository;

import com.luizalabs.logistica.desafio.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
