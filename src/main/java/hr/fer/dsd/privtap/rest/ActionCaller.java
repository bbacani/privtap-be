package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.action.Action;
import org.springframework.http.*;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ActionCaller {

    // TODO: 08.12.2022. write this more universal, I have changed it to do the spotify play action
    public void callAction(String url, Action action){
        WebClient client = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + "BQAaJaMnFgzJPtHomxiWqKwwH0cu7uL3MoxbVrccZS9Q4YsxBqlm8dfWDyKtNn5zrnjo6ptPux5J7yF9qIm77IDFxdX_-O0Rfj3TrtTAnHGf1o5em5Q6kfMcLuw0q5_f3hvReQ6c3H4E1Ukq_ReNKk8FHHYqXdm54XSprRq-lsIadlA6xCvhJ1eWRuGM5LY4fwtnl3J9pM6xiA")
                .build();

        // TODO: 08.12.2022. create a switch case here for post/put/delete
        client.put()
                .uri(url)
                .body(Mono.just(action), Action.class)
                .retrieve()
                .toEntity(String.class)
                .subscribe(stringResponseEntity -> {});
    }
}
