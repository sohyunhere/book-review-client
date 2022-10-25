package com.example.bookreviewclient.test.controller;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@RestController
public class WebClientController {

    @GetMapping("/test")
    public Mono<String> doTest() {
        WebClient client = WebClient.create();
        return client.get()
                .uri("http://localhost:5011/webclient/test-create")
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping("/test2")
    public Mono<String> doTest2() {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(
                        client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000) //miliseconds
                                .doOnConnected(
                                        conn -> conn.addHandlerLast(new ReadTimeoutHandler(5))  //sec
                                                .addHandlerLast(new WriteTimeoutHandler(60)) //sec
                                )
                );

        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:5011")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return client.get()
                .uri("/webclient/test-builder")
                .retrieve()
                .bodyToMono(String.class);
    }

}