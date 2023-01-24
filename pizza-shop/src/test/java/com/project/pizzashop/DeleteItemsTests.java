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
public class DeleteItemsTests {
    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    RestaurantRepository restaurants;
    @Autowired
    PizzaRepository pizzas;
    /*@Test
    public void deleteRestaurant() throws Exception {
        HttpEntity<String> request = new HttpEntity<>("/1", new HttpHeaders());
        String body = restTemplate.postForEntity("http://localhost:" + port + "/delete_rest", request, String.class).getBody();
        assertEquals(body, "Error!\nRate value must be in range (1, 5)\n");
        Optional<Restaurant> rest_ = restaurants.findByName("Test Pizza Restaurant");
        rest_.ifPresent(restaurant -> restaurants.deleteById(restaurant.getRest_id()));
    }*/

}
