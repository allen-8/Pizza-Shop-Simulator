package com.project.pizzashop.controller;

import com.project.pizzashop.entity.Order;
import com.project.pizzashop.entity.Pizza;
import com.project.pizzashop.entity.Restaurant;
import com.project.pizzashop.entity.User;
import com.project.pizzashop.repository.OrderRepository;
import com.project.pizzashop.repository.PizzaRepository;
import com.project.pizzashop.repository.RestaurantRepository;
import com.project.pizzashop.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class RestaurantController {
    @Autowired
    RestaurantRepository restaurants;
    @Autowired
    PizzaRepository pizzas;
    @Autowired
    UserRepository users;
    @Autowired
    OrderRepository orders;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add_rest")
    public ResponseEntity<String> addRest(@Valid @RequestBody Restaurant rest) {
        restaurants.save(rest);
        return new ResponseEntity<>("New Restaurant successfully added to catalog", HttpStatus.CREATED);
    }

    @GetMapping("/add_pizza")
    public ResponseEntity<String> addPizza(@RequestParam(name = "name") String name,
                                           @RequestParam(name = "restaurant") String restaurant,
                                           @RequestParam(name = "description") String description,
                                           @RequestParam(name = "small_price") String small_price,
                                           @RequestParam(name = "medium_price") String medium_price,
                                           @RequestParam(name = "big_price") String big_price,
                                           @RequestParam(name = "photo") String photo) {
        Optional<Restaurant> rest = restaurants.findByName(restaurant);
        if (rest.isEmpty())
            return new ResponseEntity<>("Error!\nRestaurant " + restaurant + " doesn't exist in database",
                    HttpStatus.BAD_REQUEST);
        Pizza pizza = new Pizza(name, rest.get(), description,
                (Objects.equals(small_price, "")) ? -1 : Double.parseDouble(small_price),
                (Objects.equals(medium_price, "")) ? -1 : Double.parseDouble(medium_price),
                (Objects.equals(big_price, "")) ? -1 : Double.parseDouble(big_price), photo);
        pizzas.save(pizza);
        return new ResponseEntity<>("New Pizza successfully added to catalog", HttpStatus.CREATED);
    }

    @PostMapping("/delete_rest/{id}")
    public ResponseEntity<String> deleteRest(@PathVariable(name = "id") String id) {
        Optional<Restaurant> rest = restaurants.findById(Integer.valueOf(id));
        if (rest.isEmpty())
            return new ResponseEntity<>("Error!\nRestaurant with id " + id + " doesn't exist", HttpStatus.BAD_REQUEST);
        restaurants.delete(rest.get());
        return new ResponseEntity<>("Restaurant '" + rest.get().getName() + "' is successfully deleted from database",
                HttpStatus.OK);
    }

    @PostMapping("/delete_pizza/{id}")
    public ResponseEntity<String> deletePizza(@PathVariable(name = "id") String id) {
        Optional<Pizza> pizza = pizzas.findById(Integer.valueOf(id));
        if (pizza.isEmpty())
            return new ResponseEntity<>("Error!\nPizza with id " + id + " doesn't exist", HttpStatus.BAD_REQUEST);
        pizzas.delete(pizza.get());
        return new ResponseEntity<>("Pizza '" + pizza.get().getName() + "' is successfully deleted from database",
                HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        sb.append("Error!\n");
        ex.getBindingResult().getAllErrors().forEach(error ->
            sb.append(error.getDefaultMessage()).append("\n"));
        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/restlist")
    public String restlist(Model model)
    {
        List<Restaurant> rests  = restaurants.findAll();
        rests.sort(Comparator.comparing(Restaurant::getName));
        model.addAttribute("rests", rests);
        return "restlist";
    }

    @GetMapping("/pizzalist")
    public String pizzalist(Model model)
    {
        List<Pizza> pizzas_  = pizzas.findAll();
        pizzas_.sort(Comparator.comparing(Pizza::getName));
        model.addAttribute("pizzas", pizzas_);
        return "pizzalist";
    }
    @GetMapping("/search_rest")
    public String searchRest(@RequestParam(name = "city") String city,
                         @RequestParam(name = "rate") double rate,
                         @RequestParam(name = "sort") String sort, Model model)
    {
        List<Restaurant> rests = restaurants.findAll();
        if (!Objects.equals(city, ""))
            rests = rests.stream().filter(r -> Objects.equals(r.getCity(), city)).toList();
        if (rate != 1)
            rests = rests.stream().filter(r -> r.getRate() >= rate).toList();
        if (Objects.equals(sort, "name"))
            rests = rests.stream().sorted(Comparator.comparing(Restaurant::getName)).toList();
        else
            rests = rests.stream().sorted(Comparator.comparing(Restaurant::getRate)).toList();
        model.addAttribute("rests", rests);
        return "restlist";
    }
    @GetMapping("/search_pizza")
    public String searchPizza(@RequestParam(name = "rest") String rest,
                         @RequestParam(name = "ing") String ing, @RequestParam(name = "size") String size,
                         @RequestParam(name = "sort") String sort, Model model)
    {
        List<Pizza> pizzas_ = pizzas.findAll();
        if (!Objects.equals(rest, ""))
            pizzas_ = pizzas_.stream().filter(r -> Objects.equals(r.getRestaurant().getName(), rest)).toList();
        if (!Objects.equals(ing, "")) {
            String[] ings = ing.split(" ");
            for (String ingredient : ings)
                if (ingredient.length() != 1)
                    pizzas_ = pizzas_.stream().filter(r -> r.getDescription().contains(ingredient.substring(1))).toList();
        }
        switch (size) {
            case "small" -> pizzas_ = pizzas_.stream().filter(r -> r.getSmall_price() != -1).toList();
            case "medium" -> pizzas_ = pizzas_.stream().filter(r -> r.getMedium_price() != -1).toList();
            case "big" -> pizzas_ = pizzas_.stream().filter(r -> r.getBig_price() != -1).toList();
        }
        if (Objects.equals(sort, "name"))
            pizzas_ = pizzas_.stream().sorted(Comparator.comparing(Pizza::getName)).toList();
        else
            pizzas_ = pizzas_.stream().sorted(Comparator.comparing(o -> o.getRestaurant().getName())).toList();
        model.addAttribute("pizzas", pizzas_);
        return "pizzalist";
    }
    @GetMapping("/restinfo")
    public String restinfo(@RequestParam(name = "id") Integer id, Model model) {
        Optional<Restaurant> rest = restaurants.findById(id);
        if (rest.isEmpty())
            return "restpage";
        List<String> info = new ArrayList<>();
        Restaurant rest_ = rest.get();
        info.add(rest_.getName());
        info.add(rest_.getAddress() + ", " + rest_.getCity());
        info.add(rest_.getEmail());
        info.add((Objects.equals(rest_.getPhone(), "")) ? "-" : rest_.getPhone());
        info.add(String.valueOf(rest_.getRate()));
        info.add(rest_.getPhoto());
        model.addAttribute("restinfo", info);
        List<Pizza> menu = pizzas.findAll();
        menu = menu.stream().filter(r -> Objects.equals(r.getRestaurant().getName(), rest_.getName())).toList();
        model.addAttribute("menu", menu);
        return "restpage";
    }
    @GetMapping("/pizzainfo")
    public String pizzainfo(@RequestParam(name = "id") Integer id, Model model) {
        Optional<Pizza> pizza = pizzas.findById(id);
        if (pizza.isEmpty())
            return "pizzapage";
        List<String> info = new ArrayList<>();
        Pizza pizza_ = pizza.get();
        info.add(pizza_.getName());
        info.add(pizza_.getRestaurant().getName());
        info.add(pizza_.getDescription());
        info.add((pizza_.getSmall_price() == -1) ? "unavailable" : (pizza_.getSmall_price() + "$"));
        info.add((pizza_.getMedium_price() == -1) ? "unavailable" : (pizza_.getMedium_price() + "$"));
        info.add((pizza_.getBig_price() == -1) ? "unavailable" : (pizza_.getBig_price() + "$"));
        info.add(pizza_.getPhoto());
        model.addAttribute("pizzainfo", info);
        return "pizzapage";
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        users.save(user);
        return new ResponseEntity<>("Welcome, " + user.getName() +
                ((Objects.equals(user.getSurname(), "")) ? "" : " ") + user.getSurname() + "!", HttpStatus.CREATED);
    }

    @GetMapping("/checklogin")
    public String checkLogin(Model model) {
        Optional<User> active = users.findByIsActive(true);
        if (active.isEmpty())
            return "redirect:/login.html";
        model.addAttribute("user", active.get());
        return "userpage";
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam(name = "email") String email,
                                        @RequestParam(name = "password") String pass) {
        Optional<User> user = users.findByEmail(email);
        if (user.isEmpty())
            return new ResponseEntity<>("Error!\nInvalid email", HttpStatus.BAD_REQUEST);
        if (!Objects.equals(user.get().getPassword(), pass))
            return new ResponseEntity<>("Error!\nWrong password!", HttpStatus.BAD_REQUEST);
        User user_ = user.get();
        Optional<User> old = users.findByIsActive(true);
        if (old.isPresent()) {
            old.get().setIsActive(false);
            users.save(old.get());
        }
        user_.setIsActive(true);
        users.save(user_);
        return new ResponseEntity<>("Welcome, " + user_.getName() +
                ((Objects.equals(user_.getSurname(), "")) ? "" : " ") + user_.getSurname() + "!", HttpStatus.OK);
    }
    @GetMapping("/showcard")
    public String showCard(Model model) {
        List<Order> order = orders.findAll();
        if (order.isEmpty())
            return "card";
        double sum = 0;
        for (Order o : order)
            sum += o.getPrice();
        String rest = order.get(0).getPizza().getRestaurant().getName();
        model.addAttribute("orders", order);
        model.addAttribute("price", sum);
        model.addAttribute("rest", rest);
        return "card";
    }

    @GetMapping("/addtocard")
    public ResponseEntity<String> addToCard(@RequestParam(name = "pizza") String pizza,
                                        @RequestParam(name = "size") String size) {
        String[] pizzaid = pizza.split("=");
        Optional<Pizza> opt = pizzas.findById(Integer.parseInt(pizzaid[1]));
        if (opt.isEmpty())
            throw new IllegalArgumentException("Something went wrong...");
        Pizza p = opt.get();
        double price = 0;
        switch (size) {
            case "Small" -> price = p.getSmall_price();
            case "Medium" -> price = p.getMedium_price();
            case "Big" -> price = p.getBig_price();
        }
        if (price == -1)
            return new ResponseEntity<>(size + " size is unavailable!", HttpStatus.BAD_REQUEST);
        List<Order> ord = orders.findAll();
        if (!ord.isEmpty() && !Objects.equals(ord.get(0).getPizza().getRestaurant().getName(), p.getRestaurant().getName()))
            return new ResponseEntity<>("You cannot buy pizzas in different restaurants in one order!",
                    HttpStatus.BAD_REQUEST);
        Order order = new Order(p, size, price);
        orders.save(order);
        return new ResponseEntity<>(size + " " + p.getName() + " was added to your Card", HttpStatus.OK);
    }
    @GetMapping("/removepizza")
    public String removePizzaFromOrder(@RequestParam(name = "id") Integer id, Model model) {
        orders.deleteById(id);
        List<Order> order = orders.findAll();
        if (order.isEmpty())
            return "card";
        double sum = 0;
        for (Order o : order)
            sum += o.getPrice();
        String rest = order.get(0).getPizza().getRestaurant().getName();
        model.addAttribute("orders", order);
        model.addAttribute("price", sum);
        model.addAttribute("rest", rest);
        return "card";
    }
    @GetMapping("/payment")
    public ResponseEntity<String> payment() {
        orders.deleteAll();
        return new ResponseEntity<>("Thank You! Your order is on the way!", HttpStatus.OK);
    }
    @GetMapping("/check")
    public String check(@RequestParam(name = "dest") String destination) {
        Optional<User> active = users.findByIsActive(true);
        if (active.isEmpty() || !Objects.equals(active.get().getEmail(), "admin@pizza.com"))
            return "redirect:/forbidden.html";
        return "redirect:/" + destination;
    }
    @GetMapping("/changename")
    public ResponseEntity<String> changeName(@RequestParam(name = "password") String pass,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "surname") String surname) {
        Optional<User> active = users.findByIsActive(true);
        if (active.isEmpty())
            throw new IllegalArgumentException("Critical mistake in code...");
        User user = active.get();
        if (!Objects.equals(pass, user.getPassword()))
            return new ResponseEntity<>("Password is incorrect!", HttpStatus.BAD_REQUEST);
        if (!Objects.equals(name, ""))
            user.setName(name);
        if (!Objects.equals(surname, ""))
            user.setSurname(surname);
        users.save(user);
        return new ResponseEntity<>("Changes applied successfully!", HttpStatus.OK);
    }
    @GetMapping("/changepassword")
    public ResponseEntity<String> changeEmail(@RequestParam(name = "old") String old,
                                             @RequestParam(name = "new") String pass) {
        Optional<User> active = users.findByIsActive(true);
        if (active.isEmpty())
            throw new IllegalArgumentException("Critical mistake in code...");
        User user = active.get();
        if (!Objects.equals(old, user.getPassword()))
            return new ResponseEntity<>("Password is incorrect!", HttpStatus.BAD_REQUEST);
        if (pass.length() < 6)
            return new ResponseEntity<>("Error!\nNew password is too short!", HttpStatus.BAD_REQUEST);
        user.setPassword(pass);
        users.save(user);
        return new ResponseEntity<>("Password changed successfully!", HttpStatus.OK);
    }
    @GetMapping("/logoff")
    public String logOff() {
        Optional<User> active = users.findByIsActive(true);
        if (active.isEmpty())
            throw new IllegalArgumentException("Critical mistake in code...");
        User user = active.get();
        user.setIsActive(false);
        orders.deleteAll();
        users.save(user);
        return "redirect:/";
    }
}
