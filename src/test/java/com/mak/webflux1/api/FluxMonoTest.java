package com.mak.webflux1.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mak.webflux1.dto.User;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @SpringBootTest
@WebFluxTest
public class FluxMonoTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void flux_test1() {
        Flux<Integer> fluxClient = webTestClient.get().uri("/api/flux-int")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(fluxClient)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .verifyComplete();

        System.out.println("Test1 ends!");
    }

    @Test
    public void flux_test2() {
        webTestClient.get().uri("/api/flux-int")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Integer.class)
                .hasSize(2);

        System.out.println("Test2 ends!");
    }

    @Test
    public void flux_test3() {
        webTestClient.get().uri("/api/flux-user")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_NDJSON_VALUE)
                .expectBodyList(User.class)
                .hasSize(2);

        System.out.println("Test3 ends!");
    }

    @Test
    public void flux_stream1() {
        Flux<Long> longStreamFlux = webTestClient.get().uri("/api/flux-int-stream")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(longStreamFlux)
                .expectNext(0l, 1l, 2l)
                .thenCancel()
                .verify();

        System.out.println("Test stream1 ends!");
    }

}
