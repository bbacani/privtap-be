package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.action.Action;
import org.springframework.http.*;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ActionCaller {

    public void callAction(String url, Action action){
        WebClient client = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        client.post()
                .uri(url)
                .body(Mono.just(action), Action.class)
                .retrieve()
                .toEntity(String.class)
                .subscribe(stringResponseEntity -> {});
    }
}
