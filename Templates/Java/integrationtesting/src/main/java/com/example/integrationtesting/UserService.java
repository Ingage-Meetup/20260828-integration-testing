package com.example.integrationtesting;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    Optional<User> getById(String id);
    List<AddressUser> getNearby(double lat, double lng, double miles);
}
