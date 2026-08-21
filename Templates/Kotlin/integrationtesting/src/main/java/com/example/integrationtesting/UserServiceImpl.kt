package com.example.integrationtesting

import com.example.integrationtesting.DistanceUtils.calculateDistance
import org.springframework.stereotype.Service
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.MapperFeature
import tools.jackson.databind.json.JsonMapper
import kotlin.streams.asSequence;

@Service
internal class UserServiceImpl(private val client: JsonPlaceholderClient) : UserService {
    private val mapper: JsonMapper = JsonMapper
        .builder()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, false)
        .build()

    override val all: MutableList<User?>?
        get() {
            val string = client.get("/users", null)
            val json = mapper.readTree(string)
            return mapper.convertValue<MutableList<User?>?>(
                json,
                object :
                    TypeReference<MutableList<User?>?>() {})
        }

    override fun getById(id: String): User? {
        val string = client.get("/users", null)
        val json = mapper.readTree(string)
        val allUsers = mapper.convertValue<MutableList<User?>>(json, object : TypeReference<MutableList<User?>?>() {})
        val user: User? = allUsers.stream().asSequence().filter { u: User? -> id == u?.id.toString() }.firstOrNull();
        return user
    }

    override fun getNearby(lat: Double, lng: Double, miles: Double): MutableList<AddressUser?>? {
        val string = client.get("/users", null)
        val json = mapper.readTree(string)
        val allUsers = mapper.convertValue<MutableList<AddressUser?>>(
            json,
            object : TypeReference<MutableList<AddressUser?>?>() {})
        return allUsers.stream().filter { user: AddressUser? ->
            user!!.address!!.geo!!.isPresent() &&
                    calculateDistance(
                        lat,
                        lng,
                        user.address.geo.get().lat,
                        user.address.geo.get().lng
                    ) <= miles
        }.toList()
    }
}