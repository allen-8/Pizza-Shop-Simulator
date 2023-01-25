package com.project.pizzashop;
import com.project.pizzashop.entity.Pizza;
import com.project.pizzashop.entity.Restaurant;
import com.project.pizzashop.entity.User;
import com.project.pizzashop.repository.UserRepository;
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
public class LoginTests {
    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    UserRepository users;
    @Test
    public void loginCorrect() throws Exception {
        users.save(new User(0, "Test", "User", "test@pizza.com", false, "password"));
        Map<String, String> vars = new HashMap<>();
        vars.put("email", "test@pizza.com");
        vars.put("password", "password");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/login?email={email}&password=" +
                        "{password}", String.class, vars).getBody();
        assertEquals(body, "Welcome, Test User!");
        Optional<User> user = users.findByEmail("test@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
    @Test
    public void loginWithIncorrectEmail() throws Exception {
        users.save(new User(0, "Test", "User", "test@pizza.com", false, "password"));
        Map<String, String> vars = new HashMap<>();
        vars.put("email", "incorrect@pizza.com");
        vars.put("password", "password");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/login?email={email}&password=" +
                "{password}", String.class, vars).getBody();
        assertEquals(body, "Error!\nInvalid email");
        Optional<User> user = users.findByEmail("test@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
    @Test
    public void loginWithIncorrectPassword() throws Exception {
        users.save(new User(0, "Test", "User", "test@pizza.com", false, "password"));
        Map<String, String> vars = new HashMap<>();
        vars.put("email", "test@pizza.com");
        vars.put("password", "incorrect");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/login?email={email}&password=" +
                "{password}", String.class, vars).getBody();
        assertEquals(body, "Error!\nWrong password!");
        Optional<User> user = users.findByEmail("test@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
}