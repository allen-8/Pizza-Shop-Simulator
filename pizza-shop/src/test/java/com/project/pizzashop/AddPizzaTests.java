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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class AddPizzaTests {
    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    RestaurantRepository restaurants;
    @Autowired
    PizzaRepository pizzas;
    @Test
    public void addPizzaCorrectly() throws Exception {
        restaurants.save(new Restaurant("Test Cafe", "Berlin", "address", "email@pizza.com",
                "4537658", 3.4, ""));
        Map<String, String> vars = new HashMap<>();
        vars.put("name", "Test Pizza");
        vars.put("restaurant", "Test Cafe");
        vars.put("description", "Pizza");
        vars.put("small_price", "3.5");
        vars.put("medium_price", "5.2");
        vars.put("big_price", "8.7");
        vars.put("photo", "url");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/add_pizza?name={name}&restaurant=" +
                        "{restaurant}&description={description}&small_price={small_price}&medium_price={medium_price}" +
                "&big_price={big_price}&photo={photo}",
                String.class, vars).getBody();
        assertEquals(body, "New Pizza successfully added to catalog");
        Optional<Pizza> pizza_ = pizzas.findByName("Test Pizza");
        pizza_.ifPresent(pizza -> pizzas.deleteById(pizza.getPizza_id()));
        restaurants.deleteById(restaurants.findByName("Test Cafe").get().getRest_id());
    }
    @Test
    public void addPizzaWithIncorrectRestaurant() throws Exception {
        Map<String, String> vars = new HashMap<>();
        vars.put("name", "Test Pizza");
        vars.put("restaurant", "Test Cafe");
        vars.put("description", "Pizza");
        vars.put("small_price", "3.5");
        vars.put("medium_price", "5.2");
        vars.put("big_price", "8.7");
        vars.put("photo", "url");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/add_pizza?name={name}&restaurant=" +
                        "{restaurant}&description={description}&small_price={small_price}&medium_price={medium_price}" +
                        "&big_price={big_price}&photo={photo}",
                String.class, vars).getBody();
        assertEquals(body, "Error!\nRestaurant Test Cafe doesn't exist in database");
        Optional<Pizza> pizza_ = pizzas.findByName("Test Pizza");
        pizza_.ifPresent(pizza -> pizzas.deleteById(pizza.getPizza_id()));
    }
    @Test
    public void addPizzaWithoutDescription() throws Exception {
        restaurants.save(new Restaurant("Test Cafe", "Berlin", "address", "email@pizza.com",
                "4537658", 3.4, ""));
        Map<String, String> vars = new HashMap<>();
        vars.put("name", "Test Pizza");
        vars.put("restaurant", "Test Cafe");
        vars.put("description", "");
        vars.put("small_price", "3.5");
        vars.put("medium_price", "5.2");
        vars.put("big_price", "8.7");
        vars.put("photo", "url");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/add_pizza?name={name}&restaurant=" +
                        "{restaurant}&description={description}&small_price={small_price}&medium_price={medium_price}" +
                        "&big_price={big_price}&photo={photo}",
                String.class, vars).getBody();
        assertEquals(body, "New Pizza successfully added to catalog");
        Optional<Pizza> pizza_ = pizzas.findByName("Test Pizza");
        pizza_.ifPresent(pizza -> pizzas.deleteById(pizza.getPizza_id()));
        restaurants.deleteById(restaurants.findByName("Test Cafe").get().getRest_id());
    }
    @Test
    public void addPizzaWithoutSmallPrice() throws Exception {
        restaurants.save(new Restaurant("Test Cafe", "Berlin", "address", "email@pizza.com",
                "4537658", 3.4, ""));
        Map<String, String> vars = new HashMap<>();
        vars.put("name", "Test Pizza");
        vars.put("restaurant", "Test Cafe");
        vars.put("description", "Pizza");
        vars.put("small_price", "");
        vars.put("medium_price", "5.2");
        vars.put("big_price", "8.7");
        vars.put("photo", "url");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/add_pizza?name={name}&restaurant=" +
                        "{restaurant}&description={description}&small_price={small_price}&medium_price={medium_price}" +
                        "&big_price={big_price}&photo={photo}",
                String.class, vars).getBody();
        assertEquals(body, "New Pizza successfully added to catalog");
        Optional<Pizza> pizza_ = pizzas.findByName("Test Pizza");
        pizza_.ifPresent(pizza -> pizzas.deleteById(pizza.getPizza_id()));
        restaurants.deleteById(restaurants.findByName("Test Cafe").get().getRest_id());
    }
    @Test
    public void addPizzaWithoutMediumPrice() throws Exception {
        restaurants.save(new Restaurant("Test Cafe", "Berlin", "address", "email@pizza.com",
                "4537658", 3.4, ""));
        Map<String, String> vars = new HashMap<>();
        vars.put("name", "Test Pizza");
        vars.put("restaurant", "Test Cafe");
        vars.put("description", "Pizza");
        vars.put("small_price", "3.5");
        vars.put("medium_price", "");
        vars.put("big_price", "8.7");
        vars.put("photo", "url");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/add_pizza?name={name}&restaurant=" +
                        "{restaurant}&description={description}&small_price={small_price}&medium_price={medium_price}" +
                        "&big_price={big_price}&photo={photo}",
                String.class, vars).getBody();
        assertEquals(body, "New Pizza successfully added to catalog");
        Optional<Pizza> pizza_ = pizzas.findByName("Test Pizza");
        pizza_.ifPresent(pizza -> pizzas.deleteById(pizza.getPizza_id()));
        restaurants.deleteById(restaurants.findByName("Test Cafe").get().getRest_id());
    }
    @Test
    public void addPizzaWithoutBigPrice() throws Exception {
        restaurants.save(new Restaurant("Test Cafe", "Berlin", "address", "email@pizza.com",
                "4537658", 3.4, ""));
        Map<String, String> vars = new HashMap<>();
        vars.put("name", "Test Pizza");
        vars.put("restaurant", "Test Cafe");
        vars.put("description", "Pizza");
        vars.put("small_price", "3.5");
        vars.put("medium_price", "5.2");
        vars.put("big_price", "");
        vars.put("photo", "url");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/add_pizza?name={name}&restaurant=" +
                        "{restaurant}&description={description}&small_price={small_price}&medium_price={medium_price}" +
                        "&big_price={big_price}&photo={photo}",
                String.class, vars).getBody();
        assertEquals(body, "New Pizza successfully added to catalog");
        Optional<Pizza> pizza_ = pizzas.findByName("Test Pizza");
        pizza_.ifPresent(pizza -> pizzas.deleteById(pizza.getPizza_id()));
        restaurants.deleteById(restaurants.findByName("Test Cafe").get().getRest_id());
    }
    @Test
    public void addPizzaWithoutPhoto() throws Exception {
        restaurants.save(new Restaurant("Test Cafe", "Berlin", "address", "email@pizza.com",
                "4537658", 3.4, ""));
        Map<String, String> vars = new HashMap<>();
        vars.put("name", "Test Pizza");
        vars.put("restaurant", "Test Cafe");
        vars.put("description", "Pizza");
        vars.put("small_price", "3.5");
        vars.put("medium_price", "5.2");
        vars.put("big_price", "8.7");
        vars.put("photo", "");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/add_pizza?name={name}&restaurant=" +
                        "{restaurant}&description={description}&small_price={small_price}&medium_price={medium_price}" +
                        "&big_price={big_price}&photo={photo}",
                String.class, vars).getBody();
        assertEquals(body, "New Pizza successfully added to catalog");
        Optional<Pizza> pizza_ = pizzas.findByName("Test Pizza");
        pizza_.ifPresent(pizza -> pizzas.deleteById(pizza.getPizza_id()));
        restaurants.deleteById(restaurants.findByName("Test Cafe").get().getRest_id());
    }

}
