package com.example.integrationtesting

interface UserService {
    val all: MutableList<User?>?
    fun getById(id: String): User?
    fun getNearby(lat: Double, lng: Double, miles: Double): MutableList<AddressUser?>?
}
