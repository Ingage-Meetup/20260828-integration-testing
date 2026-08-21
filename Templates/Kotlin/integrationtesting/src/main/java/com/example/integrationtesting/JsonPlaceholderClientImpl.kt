package com.example.integrationtesting

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class JsonPlaceholderClientImpl(@Value($$"${httpClientSettings.baseUrl}") baseUrl: String) : JsonPlaceholderClient {
    private val client: RestClient = RestClient.builder().baseUrl(baseUrl).build()

    override fun get(path: String?, queryString: String?): String? {
        val url = path + (queryString ?: "")
        val response = client.get().uri(url).retrieve().body(String::class.java)
        return response
    }
}
