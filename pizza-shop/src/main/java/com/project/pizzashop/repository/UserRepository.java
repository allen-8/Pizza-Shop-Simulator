package com.project.pizzashop.repository;

import com.project.pizzashop.entity.Restaurant;
import com.project.pizzashop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByIsActive(boolean state);
}
