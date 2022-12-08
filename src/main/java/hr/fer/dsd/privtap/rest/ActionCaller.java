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
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + "BQBF_kRf2FmIRa0EuskfHF54IxwG19p0RyH9JXXjClCOI2waTzcXTTR62kgteMsfZIsd6_nHJHwBmLA22NRUX9r56q0HkII3DMXjngmrXykQznRZhRFX-lW00rqzDrRtiqPnarsVZu1bqPZCpHPiloLvnEn9DbRzBnM2hw1OZ6kTYiH7xOw5776qBNqZLSLRPGaXm5EiR4P49Q")
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
