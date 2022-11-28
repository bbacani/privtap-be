package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.action.Action;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
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

    public void secondAttempt(String url, Action action){
        WebClient client = WebClient.create();
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.post();
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(url);
    }

}
