package com.example.integrationtesting

import java.util.*

@JvmRecord
data class User(
        @JvmField val id: Int,
        val name: String?,
        val username: String?,
        val email: String?,
        val phone: String?,
        val website: String?,
        val company: Company?
)

@JvmRecord
data class AddressUser(
        @JvmField val id: Int,
        val name: String?,
        val username: String?,
        val email: String?,
        @JvmField val address: Address?,
        val phone: String?,
        val website: String?,
        val company: Company?
)

@JvmRecord
data class Address(
        val street: String?,
        val suite: String?,
        val city: String?,
        val zipcode: String?,
        @JvmField val geo: Optional<Geo?>?
)

@JvmRecord
data class Company(val name: String?, val catchPhrase: String?, val bs: String?)

@JvmRecord
data class Geo(@JvmField val lat: String, @JvmField val lng: String)