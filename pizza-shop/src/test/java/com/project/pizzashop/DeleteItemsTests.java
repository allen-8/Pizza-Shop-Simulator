package com.project.pizzashop;

import com.project.pizzashop.entity.Pizza;
import com.project.pizzashop.entity.Restaurant;
import com.project.pizzashop.repository.PizzaRepository;
import com.project.pizzashop.repository.RestaurantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class DeleteItemsTests {
    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    RestaurantRepository restaurants;
    @Autowired
    PizzaRepository pizzas;
    @Test
    public void deleteRestaurantCorrect() throws Exception {
        try {
            restaurants.save(new Restaurant("Test Cafe", "Berlin", "address", "email@pizza.com",
                    "4537658", 3.4, ""));
            Map<String, String> map= new HashMap<>();
            HttpEntity<Map<String, String>> request = new HttpEntity<>(map, new HttpHeaders());
            ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/delete_rest/" +
                    restaurants.findByName("Test Cafe").get().getRest_id(), HttpMethod.POST, request, String.class);
            assertEquals(response.getBody(), "Restaurant Test Cafe is successfully deleted from database");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Optional<Restaurant> test = restaurants.findByName("Test Cafe");
            test.ifPresent(rest -> restaurants.deleteById(rest.getRest_id()));
            throw ex;
        }
        finally {
            Optional<Restaurant> test = restaurants.findByName("Test Cafe");
            test.ifPresent(rest -> restaurants.deleteById(rest.getRest_id()));
        }
    }
    @Test
    public void deleteRestaurantIncorrect() throws Exception {
            Map<String, String> map= new HashMap<>();
            HttpEntity<Map<String, String>> request = new HttpEntity<>(map, new HttpHeaders());
            ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/delete_rest/" +
                    -1, HttpMethod.POST, request, String.class);
            assertEquals(response.getBody(), "Error!\nRestaurant with id -1 doesn't exist");
    }

    @Test
    public void deletePizzaCorrect() throws Exception {
        Restaurant r = new Restaurant("Test Cafe", "Berlin", "address", "email@pizza.com",
                "4537658", 3.4, "");
        restaurants.save(r);
        pizzas.save(new Pizza("Test Pizza", r, "", -1, -1, -1, ""));
        Map<String, String> map= new HashMap<>();
        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/delete_pizza/" +
                pizzas.findByName("Test Pizza").get().getPizza_id(), HttpMethod.POST, request, String.class);
        assertEquals(response.getBody(), "Pizza Test Pizza is successfully deleted from database");
        Optional<Restaurant> test = restaurants.findByName("Test Cafe");
        test.ifPresent(rest -> restaurants.deleteById(rest.getRest_id()));
        Optional<Pizza> test1 = pizzas.findByName("Test Pizza");
        test1.ifPresent(pizza -> pizzas.deleteById(pizza.getPizza_id()));
    }

    @Test
    public void deletePizzaIncorrect() throws Exception {
        Map<String, String> map= new HashMap<>();
        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/delete_pizza/-1", HttpMethod.POST, request, String.class);
        assertEquals(response.getBody(), "Error!\nPizza with id -1 doesn't exist");
    }

    @ExceptionHandler(Exception.class)
    public void catchException(Exception ex) {
        Optional<Restaurant> test = restaurants.findByName("Test Cafe");
        test.ifPresent(rest -> restaurants.deleteById(rest.getRest_id()));
        Optional<Pizza> test1 = pizzas.findByName("Test Pizza");
        test1.ifPresent(pizza -> pizzas.deleteById(pizza.getPizza_id()));
    }

}
