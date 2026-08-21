package com.example.integrationtesting

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) {
    @get:GetMapping("/users")
    val allUsers: MutableList<User?>?
        get() = userService.all

    @GetMapping("/user/{id}")
    fun getUserById(@PathVariable id: String): User? {
        return userService.getById(id)
    }

    @GetMapping("/users/nearby")
    fun getNearby(
        @RequestParam("lat") lat: Double,
        @RequestParam("lng") lng: Double,
        @RequestParam("miles") miles: Double
    ): MutableList<AddressUser?>? {
        return userService.getNearby(lat, lng, miles)
    }
}
