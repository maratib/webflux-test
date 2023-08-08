package com.mak.webflux1.api;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mak.webflux1.dto.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class Api {

    @GetMapping(value = "", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<String> index() {

        return Mono.just("Hello WebFlux!");
    }

    @GetMapping(value = "/flux-int")
    public Flux<Integer> flux_int() {

        return Flux.just(1, 2)
                // .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "/flux-int-stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Long> flux_int_stream() {

        return Flux.interval(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "/flux-user", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<User> flux() {
        User user1 = new User(1, "Maratib");
        User user2 = new User(2, "Musa");

        return Flux.just(user1, user2)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

}
