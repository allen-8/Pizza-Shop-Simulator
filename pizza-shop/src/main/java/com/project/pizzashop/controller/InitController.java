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
        Restaurant citypizza = new Restaurant("City Pizza", "Berlin", "Pizzastr.1, 12345", "citypizza@pizza.com",
                "+49123456789", 4.6, "https://www.alytusinfo.lt/data/tourism_objects/large/city_pizza_alytus__1_.jpg");
        Restaurant dominos = new Restaurant("Dominos Pizza", "Berlin", "Pizzastr.2, 67890", "dominos@pizza.com",
                "", 3.8, "https://lh5.googleusercontent.com/p/AF1QipOTtUTZ6Vs0_0XUfg2SLO2gjH61TulPHI8AF_Fj=w408-h306-k-no");
        Restaurant allora = new Restaurant("Pizerria Allora", "Ulm", "Ulmstr.1, 34567", "allora@pizza.com",
                "+49987654321", 4.1, "https://lh5.googleusercontent.com/p/AF1QipPWKWtPIDKs6g6SqId7v_ZIPCBIFaIpsMtyIu2N=w408-h544-k-no");
        restaurants.save(citypizza);
        restaurants.save(dominos);
        restaurants.save(allora);
        pizzas.save(new Pizza("Carbonara", citypizza, "Delicious pizza with cheese, mashrooms, onions & carbonara sause",
                5.6, 7.8, 12.3,
                "https://img.taste.com.au/6MQTA3rp/taste/2016/11/carbonara-pizza-our-most-recent-recipes-of-the-day-104195-2.jpeg"));
        pizzas.save(new Pizza("Marinara", citypizza, "Tomato Sauce, Garlic, Oregano & Basil. Vegan.",
                4.3, 6.2, 10.6,
                "https://flipdish.imgix.net/m10fmGwpZOWRKbrntJ4IuDhW7D0.jpg?auto=format&auto=compress&fit=crop&w=767&h=460&dpr=1"));
        pizzas.save(new Pizza("Anchovies", citypizza, "Anchovies, Fresh Chilies, Garlic, Capers, Mozzarella, Parsley & Tomato Sauce.",
                -1, 6.8, -1,
                "https://flipdish.imgix.net/XX17jEFwVvjLYaLvmAHO8Vw3Gl4.jpg?auto=format&auto=compress&fit=crop&w=767&h=460&dpr=1"));
        pizzas.save(new Pizza("Margarita", dominos, "Mozzarella, Parmesan, Tomato Sauce, Olive Oil & Basil.",
                4.6, 6.9, 11.3,
                "https://flipdish.imgix.net/cOpSOzJIDhRn0xJk10gIaDtfMc.jpg?auto=format&auto=compress&fit=crop&w=767&h=460&dpr=1"));
        pizzas.save(new Pizza("Salami", dominos, "",
                4.6, -1, 11.3,
                "https://imageproxy.wolt.com/menu/menu-images/620d5472a798a099bffed460/74bd5538-909e-11ec-9727-122789fc7683_02_pizza_salami.jpeg"));
        pizzas.save(new Pizza("Prosciutto", allora, "With cheese and ham",
                -1, 7.1, 10.2,
                "https://imageproxy.wolt.com/menu/menu-images/620d5472a798a099bffed460/7bc8149e-909e-11ec-8476-22b1de579f77_04_pizza_prosciutto.jpeg"));
        pizzas.save(new Pizza("Pizza Chicken", allora, "with cheese, chicken, onions & corn",
                5.2, 7.8, 10.9,
                "https://imageproxy.wolt.com/menu/menu-images/620d5472a798a099bffed460/8b41f750-909e-11ec-a239-ba447eadc14f_24_pizza_chicken.jpeg"));
    }

}
