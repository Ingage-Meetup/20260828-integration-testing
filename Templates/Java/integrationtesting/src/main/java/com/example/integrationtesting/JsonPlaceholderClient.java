package com.example.integrationtesting;

import java.util.Optional;

public interface JsonPlaceholderClient {
    String get(String path, Optional<String> queryString);
}
