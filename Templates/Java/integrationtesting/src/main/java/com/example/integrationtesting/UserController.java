package com.example.integrationtesting;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/user/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userService.getById(id);
    }
    
    @GetMapping("/users/nearby")
    public List<AddressUser> getNearby(@RequestParam("lat") double lat, @RequestParam("lng") double lng, @RequestParam("miles") double miles) {
        return userService.getNearby(lat, lng, miles);
    }
}
