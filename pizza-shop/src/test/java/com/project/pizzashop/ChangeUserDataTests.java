package com.project.pizzashop;

import com.project.pizzashop.entity.User;
import com.project.pizzashop.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ChangeUserDataTests {
    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    UserRepository users;
    @Test
    public void changeNameAndSurname() throws Exception {
        users.save(new User(0, "Test", "User", "test@pizza.com", true, "password"));
        Map<String, String> vars = new HashMap<>();
        vars.put("password", "password");
        vars.put("name", "Test1");
        vars.put("surname", "User1");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/changename?password={password}" +
                "&name={name}&surname={surname}", String.class, vars).getBody();
        assertEquals(body, "Changes applied successfully!");
        assertEquals(users.findByEmail("test@pizza.com").get().getName(), "Test1");
        assertEquals(users.findByEmail("test@pizza.com").get().getSurname(), "User1");
        Optional<User> user = users.findByEmail("test@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
    @Test
    public void changeName() throws Exception {
        users.save(new User(0, "Test", "User", "test@pizza.com", true, "password"));
        Map<String, String> vars = new HashMap<>();
        vars.put("password", "password");
        vars.put("name", "Test1");
        vars.put("surname", "");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/changename?password={password}" +
                "&name={name}&surname={surname}", String.class, vars).getBody();
        assertEquals(body, "Changes applied successfully!");
        assertEquals(users.findByEmail("test@pizza.com").get().getName(), "Test1");
        assertEquals(users.findByEmail("test@pizza.com").get().getSurname(), "User");
        Optional<User> user = users.findByEmail("test@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
    @Test
    public void changeSurname() throws Exception {
        users.save(new User(0, "Test", "User", "test@pizza.com", true, "password"));
        Map<String, String> vars = new HashMap<>();
        vars.put("password", "password");
        vars.put("name", "");
        vars.put("surname", "User1");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/changename?password={password}" +
                "&name={name}&surname={surname}", String.class, vars).getBody();
        assertEquals(body, "Changes applied successfully!");
        assertEquals(users.findByEmail("test@pizza.com").get().getName(), "Test");
        assertEquals(users.findByEmail("test@pizza.com").get().getSurname(), "User1");
        Optional<User> user = users.findByEmail("test@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
    @Test
    public void changeNameWithWrongPassword() throws Exception {
        users.save(new User(0, "Test", "User", "test@pizza.com", true, "password"));
        Map<String, String> vars = new HashMap<>();
        vars.put("password", "incorrect");
        vars.put("name", "Test1");
        vars.put("surname", "");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/changename?password={password}" +
                "&name={name}&surname={surname}", String.class, vars).getBody();
        assertEquals(body, "Password is incorrect!");
        Optional<User> user = users.findByEmail("test@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
    @Test
    public void changePasswordCorrect() throws Exception {
        users.save(new User(0, "Test", "User", "test@pizza.com", true, "password"));
        Map<String, String> vars = new HashMap<>();
        vars.put("old", "password");
        vars.put("new", "Passw0rd!");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/changepassword?old={old}" +
                "&new={new}", String.class, vars).getBody();
        assertEquals(body, "Password changed successfully!");
        assertEquals(users.findByEmail("test@pizza.com").get().getPassword(), "Passw0rd!");
        Optional<User> user = users.findByEmail("test@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
    @Test
    public void changePasswordWithIncorrectOldPassword() throws Exception {
        users.save(new User(0, "Test", "User", "test@pizza.com", true, "password"));
        Map<String, String> vars = new HashMap<>();
        vars.put("old", "pass");
        vars.put("new", "Passw0rd!");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/changepassword?old={old}" +
                "&new={new}", String.class, vars).getBody();
        assertEquals(body, "Password is incorrect!");
        Optional<User> user = users.findByEmail("test@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
    @Test
    public void changePasswordWithTooShortNewPassword() throws Exception {
        users.save(new User(0, "Test", "User", "test@pizza.com", true, "password"));
        Map<String, String> vars = new HashMap<>();
        vars.put("old", "password");
        vars.put("new", "Pass");
        String body = restTemplate.getForEntity("http://localhost:" + port + "/changepassword?old={old}" +
                "&new={new}", String.class, vars).getBody();
        assertEquals(body, "Error!\nNew password is too short!");
        Optional<User> user = users.findByEmail("test@pizza.com");
        user.ifPresent(u -> users.deleteById(u.getUser_id()));
    }
}
