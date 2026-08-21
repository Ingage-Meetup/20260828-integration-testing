package com.example.integrationtesting

interface JsonPlaceholderClient {
    fun get(path: String?, queryString: String?): String?
}
