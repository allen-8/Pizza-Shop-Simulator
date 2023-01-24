package com.project.pizzashop.repository;

import com.project.pizzashop.entity.Order;
import com.project.pizzashop.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
