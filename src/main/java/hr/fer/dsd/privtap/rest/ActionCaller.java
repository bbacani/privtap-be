package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.action.Action;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.Collections;

public class ActionCaller {

    public void callAction(String url, Action action){
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Action> request = new HttpEntity<>(action, headers);
        ResponseEntity<Action> response = template.postForEntity(url, request, Action.class);

        System.out.println("status code" + response.getStatusCode());
    }

    public void blockingCall(String url, Action action){
        WebClient client = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        ResponseEntity responseEntity = client.post()
                .uri(url)
                .body(Mono.just(action), Action.class)
                .retrieve()
                .toEntity(String.class)
                .block();

        System.out.println("status :" + responseEntity.getStatusCode());


    }

    public void blockingCall2(String url, Action action){
        WebClient client = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        Disposable responseEntity = client.post()
                .uri(url)
                .body(Mono.just(action), Action.class)
                .retrieve()
                .toEntity(String.class)
                .subscribe(stringResponseEntity -> {
                    System.out.println(stringResponseEntity);
                });
    }


}
