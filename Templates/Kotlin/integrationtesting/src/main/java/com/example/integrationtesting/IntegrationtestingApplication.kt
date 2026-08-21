package com.example.integrationtesting

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
object IntegrationtestingApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(IntegrationtestingApplication::class.java, *args)
    }
}
