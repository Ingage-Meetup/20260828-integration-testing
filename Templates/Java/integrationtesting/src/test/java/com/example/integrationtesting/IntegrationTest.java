package com.example.integrationtesting;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ActiveProfiles;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.recording.RecordingStatus;
import com.github.tomakehurst.wiremock.standalone.WireMockServerRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
class IntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @RegisterExtension
    static WireMockExtension wm = WireMockExtension.newInstance()
        .options(options().port(9080).usingFilesUnderClasspath("src/test/resources/wiremock"))
        .failOnUnmatchedRequests(false)
        .build();
    

    @BeforeAll
    static void setupGlobalProxy() {
        wm.stubFor(any(anyUrl()).atPriority(10).willReturn(aResponse().proxiedFrom("https://jsonplaceholder.typicode.com")));
        wm.startRecording(recordSpec()
                .forTarget("https://jsonplaceholder.typicode.com")
                .ignoreRepeatRequests()
                .makeStubsPersistent(true).build());
    }

    @AfterAll
    static void afterAll() {
        try { wm.stopRecording(); } catch (Exception e) { }
    }

    @Test
    public void test_getNearbyUsers() {
        var response = rest.getForEntity("/users/nearby?lat=-37.3159&lng=81.1496&miles=1200", Object.class);
        // var item = (Map) response.getBody().get(0);
        // var id = (int) item.get("id");
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
        // assertEquals(id, 1);
    }
}
