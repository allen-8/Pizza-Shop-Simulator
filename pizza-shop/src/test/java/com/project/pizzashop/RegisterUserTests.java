package com.project.pizzashop;
import com.project.pizzashop.entity.Pizza;
import com.project.pizzashop.entity.Restaurant;
import com.project.pizzashop.entity.User;
import com.project.pizzashop.repository.PizzaRepository;
import com.project.pizzashop.repository.RestaurantRepository;
import com.project.pizzashop.repository.UserRepository;
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
public class RegisterUserTests {
    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    UserRepository users;
    @Test
    public void addUserCorrect() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test\", \"surname\":\"User\", " +
                "\"email\":\"email@pizza.com\", \"password\":\"1234567\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/register", request, String.class).getBody();
        assertEquals(body, "Welcome, Test User!");
        Optional<User> user = users.findByEmail("email@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
    @Test
    public void addUserWithoutName() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"\", \"surname\":\"User\", " +
                "\"email\":\"email@pizza.com\", \"password\":\"1234567\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/register", request, String.class).getBody();
        assertEquals(body, "Error!\nUser name is mandatory\n");
        Optional<User> user = users.findByEmail("email@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
    @Test
    public void addUserWithoutSurname() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test\", \"surname\":\"\", " +
                "\"email\":\"email@pizza.com\", \"password\":\"1234567\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/register", request, String.class).getBody();
        assertEquals(body, "Welcome, Test!");
        Optional<User> user = users.findByEmail("email@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
    @Test
    public void addUserWithoutEmail() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test\", \"surname\":\"User\", " +
                "\"email\":\"\", \"password\":\"1234567\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/register", request, String.class).getBody();
        assertEquals(body, "Error!\nEmail is mandatory\n");
    }
    @Test
    public void addUserWithIncorrectEmail() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test\", \"surname\":\"User\", " +
                "\"email\":\"email_pizza.com\", \"password\":\"1234567\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/register", request, String.class).getBody();
        assertEquals(body, "Error!\nEmail is invalid\n");
    }
    @Test
    public void addUserWithoutPassword() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test\", \"surname\":\"User\", " +
                "\"email\":\"email@pizza.com\", \"password\":\"\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/register", request, String.class).getBody();
        assertEquals(body, "Error!\nPassword must contain at least 6 symbols\n");
        Optional<User> user = users.findByEmail("email@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
    @Test
    public void addUserWithTooShortPassword() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\"name\":\"Test\", \"surname\":\"User\", " +
                "\"email\":\"email@pizza.com\", \"password\":\"1234\"}", headers);
        String body = restTemplate.postForEntity("http://localhost:" + port + "/register", request, String.class).getBody();
        assertEquals(body, "Error!\nPassword must contain at least 6 symbols\n");
        Optional<User> user = users.findByEmail("email@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
}
