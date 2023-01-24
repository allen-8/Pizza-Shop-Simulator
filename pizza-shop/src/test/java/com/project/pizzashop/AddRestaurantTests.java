package com.project.pizzashop;

import com.project.pizzashop.entity.Restaurant;
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

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class AddRestaurantTests {
    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    RestaurantRepository restaurants;
    @Test
    public void addRestaurantCorrectly() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test Pizza Restaurant\", \"city\":\"Berlin\", " +
                "\"address\":\"Pizzastr.10\", \"email\":\"citypizza@pizza.com\", \"phone\":\"4912345\", " +
                "\"rate\":\"4.3\", \"photo\":\"url\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/add_rest", request, String.class).getBody();
        assertEquals(body, "New Restaurant successfully added to catalog");
        Optional<Restaurant> rest_ = restaurants.findByName("Test Pizza Restaurant");
        rest_.ifPresent(restaurant -> restaurants.deleteById(restaurant.getRest_id()));
    }
    @Test
    public void addRestaurantWithoutName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"\", \"city\":\"Berlin\", " +
                "\"address\":\"Pizzastr.10\", \"email\":\"citypizza@pizza.com\", \"phone\":\"4912345\", " +
                "\"rate\":\"4.3\", \"photo\":\"url\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/add_rest", request, String.class).getBody();
        assertEquals(body, "Error!\nRestaurant name is mandatory\n");
        Optional<Restaurant> rest_ = restaurants.findByName("Test Pizza Restaurant");
        rest_.ifPresent(restaurant -> restaurants.deleteById(restaurant.getRest_id()));
    }
    @Test
    public void addRestaurantWithoutCity() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test Pizza Restaurant\", \"city\":\"\", " +
                "\"address\":\"Pizzastr.10\", \"email\":\"test@pizza.com\", \"phone\":\"4912345\", " +
                "\"rate\":\"4.3\", \"photo\":\"url\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/add_rest", request, String.class).getBody();
        assertEquals(body, "Error!\nCity is mandatory\n");
        Optional<Restaurant> rest_ = restaurants.findByName("Test Pizza Restaurant");
        rest_.ifPresent(restaurant -> restaurants.deleteById(restaurant.getRest_id()));
    }
    @Test
    public void addRestaurantWithoutAddress() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test Pizza Restaurant\", \"city\":\"Berlin\", " +
                "\"address\":\"\", \"email\":\"citypizza@pizza.com\", \"phone\":\"4912345\", " +
                "\"rate\":\"4.3\", \"photo\":\"url\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/add_rest", request, String.class).getBody();
        assertEquals(body, "Error!\nAddress is mandatory\n");
        Optional<Restaurant> rest_ = restaurants.findByName("Test Pizza Restaurant");
        rest_.ifPresent(restaurant -> restaurants.deleteById(restaurant.getRest_id()));
    }
    @Test
    public void addRestaurantWithoutEmail() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test Pizza Restaurant\", \"city\":\"Berlin\", " +
                "\"address\":\"Pizzastr.10\", \"email\":\"\", \"phone\":\"4912345\", " +
                "\"rate\":\"4.3\", \"photo\":\"url\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/add_rest", request, String.class).getBody();
        assertEquals(body, "Error!\nEmail is mandatory\n");
        Optional<Restaurant> rest_ = restaurants.findByName("Test Pizza Restaurant");
        rest_.ifPresent(restaurant -> restaurants.deleteById(restaurant.getRest_id()));
    }
    @Test
    public void addRestaurantWithWrongEmail() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test Pizza Restaurant\", \"city\":\"Berlin\", " +
                "\"address\":\"Pizzastr.10\", \"email\":\"citypizza_pizza.com\", \"phone\":\"4912345\", " +
                "\"rate\":\"4.3\", \"photo\":\"url\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/add_rest", request, String.class).getBody();
        assertEquals(body, "Error!\nEmail is invalid\n");
        Optional<Restaurant> rest_ = restaurants.findByName("Test Pizza Restaurant");
        rest_.ifPresent(restaurant -> restaurants.deleteById(restaurant.getRest_id()));
    }
    @Test
    public void addRestaurantWithWrongPhone() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test Pizza Restaurant\", \"city\":\"Berlin\", " +
                "\"address\":\"Pizzastr.10\", \"email\":\"citypizza@pizza.com\", \"phone\":\"invalid\", " +
                "\"rate\":\"4.3\", \"photo\":\"url\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/add_rest", request, String.class).getBody();
        assertEquals(body, "Error!\nPhone format is invalid\n");
        Optional<Restaurant> rest_ = restaurants.findByName("Test Pizza Restaurant");
        rest_.ifPresent(restaurant -> restaurants.deleteById(restaurant.getRest_id()));
    }
    @Test
    public void addRestaurantWithTooLowRate() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test Pizza Restaurant\", \"city\":\"Berlin\", " +
                "\"address\":\"Pizzastr.10\", \"email\":\"citypizza@pizza.com\", \"phone\":\"4912345\", " +
                "\"rate\":\"0.5\", \"photo\":\"url\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/add_rest", request, String.class).getBody();
        assertEquals(body, "Error!\nRate value must be in range (1, 5)\n");
        Optional<Restaurant> rest_ = restaurants.findByName("Test Pizza Restaurant");
        rest_.ifPresent(restaurant -> restaurants.deleteById(restaurant.getRest_id()));
    }
    @Test
    public void addRestaurantWithTooHighRate() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test Pizza Restaurant\", \"city\":\"Berlin\", " +
                "\"address\":\"Pizzastr.10\", \"email\":\"citypizza@pizza.com\", \"phone\":\"4912345\", " +
                "\"rate\":\"10\", \"photo\":\"url\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/add_rest", request, String.class).getBody();
        assertEquals(body, "Error!\nRate value must be in range (1, 5)\n");
        Optional<Restaurant> rest_ = restaurants.findByName("Test Pizza Restaurant");
        rest_.ifPresent(restaurant -> restaurants.deleteById(restaurant.getRest_id()));
    }
}
