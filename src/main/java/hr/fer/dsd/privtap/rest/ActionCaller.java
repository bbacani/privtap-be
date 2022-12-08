package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.model.auth0.OAuthCredentials;
import org.springframework.http.*;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ActionCaller {

    // TODO: 08.12.2022. write this more universal, I have changed it to do the spotify play action
    public void callAction(String url, Action action, OAuthCredentials oAuthCredentials) {
        WebClient client = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, oAuthCredentials.getTokenType() + " " + oAuthCredentials.getAccessToken())
                .build();

        // TODO: 08.12.2022. create a switch case here for post/put/delete
        client.put()
                .uri(url)
//                .body(Mono.just(action), Action.class)
                .retrieve()
                .toEntity(String.class)
                .subscribe(stringResponseEntity -> {});
    }
}
