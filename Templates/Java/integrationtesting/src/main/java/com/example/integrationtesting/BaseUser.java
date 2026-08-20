package com.example.integrationtesting;

import java.util.Optional;

public interface BaseUser {
        int id();
        String name();
        String username();
        String email();
        String phone();
        String website();
        Company company();
}

record User(int id, String name, String username, String email, String phone, String website, Company company) implements BaseUser {}

record AddressUser(int id, String name, String username, String email, Address address, String phone, String website, Company company) implements BaseUser {}

record Address(String street, String suite, String city, String zipcode, Optional<Geo> geo) {}

record Company(String name, String catchPhrase, String bs) {}

record Geo(double lat, double lng) {}