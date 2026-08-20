package com.example.integrationtesting;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.json.JsonMapper;

@Service
public class UserServiceImpl implements UserService {
    private final JsonPlaceholderClient client;
    private final JsonMapper mapper = JsonMapper
        .builder()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, false)
        .build();

    public UserServiceImpl(JsonPlaceholderClient client) {
        this.client = client;
    }

    public List<User> getAll() {
        var string = client.get("/users", Optional.empty());
        var json = mapper.readTree(string);
        return mapper.convertValue(json, new TypeReference<List<User>>() {});
    }

    public Optional<User> getById(String id) {
        var string = client.get("/users", Optional.empty());
        var json = mapper.readTree(string);
        var allUsers = mapper.convertValue(json, new TypeReference<List<User>>() {});
        var user = allUsers.stream().filter(u -> id.equals(u.id())).findAny();
        return user;
    }

    public List<AddressUser> getNearby(double lat, double lng, double miles) {
        var string = client.get("/users", Optional.empty());
        var json = mapper.readTree(string);
        var allUsers = mapper.convertValue(json, new TypeReference<List<AddressUser>>() {});
        return allUsers.stream().filter(user ->
            user.address().geo().isPresent() &&
            DistanceUtils.calculateDistance(lat, lng, Double.parseDouble(user.address().geo().get().lat()), Double.parseDouble(user.address().geo().get().lng())) <= miles
        ).toList();
    }
}