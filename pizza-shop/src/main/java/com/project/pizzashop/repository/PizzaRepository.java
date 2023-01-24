package com.project.pizzashop.repository;

import com.project.pizzashop.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    Optional<Pizza> findByName(String name);
}
