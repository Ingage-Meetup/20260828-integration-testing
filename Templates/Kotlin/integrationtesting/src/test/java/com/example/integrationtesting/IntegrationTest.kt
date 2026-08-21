package com.example.integrationtesting

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit5.WireMockExtension
import com.github.tomakehurst.wiremock.recording.RecordingStatus
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.TestRestTemplate
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatusCode
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
internal class IntegrationTest {
    @Autowired
    private val rest: TestRestTemplate? = null

    @BeforeEach
    fun setupProxy() {
        if (wm.recordingStatus.status == RecordingStatus.Recording) {
            wm.stubFor(
                WireMock.any(WireMock.anyUrl()).atPriority(10)
                    .willReturn(WireMock.aResponse().proxiedFrom("https://jsonplaceholder.typicode.com"))
            )
        }
    }

    @Test
    fun test_getNearbyUsers() {
        val response = rest!!.getForEntity(
            "/users/nearby?lat=-37.3159&lng=81.1496&miles=1200",
            MutableList::class.java
        )
        Assertions.assertEquals(response.statusCode, HttpStatusCode.valueOf(200))
        val item = response.getBody()!![0] as MutableMap<*, *>
        val id = item["id"] as Int
        Assertions.assertEquals(id, 1)
    }

    companion object {
        @JvmStatic
        @RegisterExtension
        var wm: WireMockExtension = WireMockExtension.newInstance()
            .options(WireMockConfiguration.options().port(9080).usingFilesUnderDirectory("src/test/resources/wiremock"))
            .failOnUnmatchedRequests(false)
            .build()

        @JvmStatic
        @BeforeAll
        fun setupRecording() {
            if (wm.recordingStatus.status != RecordingStatus.Recording) {
                System.out.println("Is this happening?")
                wm.startRecording(
                    WireMock.recordSpec()
                        .forTarget("https://jsonplaceholder.typicode.com")
                        .ignoreRepeatRequests()
                        .makeStubsPersistent(true).build()
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            try {
                wm.stopRecording()
            } catch (_: Exception) {
            }
        }
    }
}
