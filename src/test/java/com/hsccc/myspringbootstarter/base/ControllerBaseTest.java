package com.hsccc.myspringbootstarter.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ControllerBaseTest {

    @Autowired
    WebTestClient webClient;

    public WebTestClient.ResponseSpec post(String url, Object body, Class<?> clazz) {
        return webClient.post().uri(url)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(body), clazz)
                .exchange();
    }

    public WebTestClient.RequestBodySpec postBeforeExchange(String url, String headerName, String... headerValues) {
        return webClient.post().uri(url)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(headerName, headerValues);
    }

    public WebTestClient.ResponseSpec post(String url, Object body, Class<?> clazz, String headerName,
                                           String... headerValues) {
        return webClient.post().uri(url)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(body), clazz)
                .header(headerName, headerValues)
                .exchange();
    }

    public WebTestClient.ResponseSpec postWithOk(String url, Object body, Class<?> clazz) {
        return post(url, body, clazz).expectStatus().isOk();
    }

    public WebTestClient.ResponseSpec postWithForbidden(String url, Object body, Class<?> clazz) {
        return post(url, body, clazz).expectStatus().isForbidden();
    }
}
