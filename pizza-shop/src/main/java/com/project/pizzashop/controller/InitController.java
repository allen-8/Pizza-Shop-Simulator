package com.project.pizzashop.controller;

import com.project.pizzashop.entity.User;
import com.project.pizzashop.repository.OrderRepository;
import com.project.pizzashop.repository.PizzaRepository;
import com.project.pizzashop.repository.RestaurantRepository;
import com.project.pizzashop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class InitController implements CommandLineRunner {
    private RestaurantRepository restaurants;
    private PizzaRepository pizzas;
    private UserRepository users;
    private OrderRepository orders;
    @Autowired
    public InitController(RestaurantRepository restaurants, PizzaRepository pizzas, UserRepository users, OrderRepository orders) {
        this.restaurants = restaurants;
        this.pizzas = pizzas;
        this.users = users;
        this.orders = orders;
    }
    @Override
    public void run(String... args) throws Exception {
        orders.deleteAll();
        users.save(new User(1, "Admin", "", "admin@pizza.com", false, "Passw0rd!"));
    }

}
