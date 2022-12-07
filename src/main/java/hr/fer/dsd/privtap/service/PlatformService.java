package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.entities.PlatformEntity;
import hr.fer.dsd.privtap.domain.repositories.PlatformRepository;
import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.model.auth0.AuthTokenResponse;
import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.model.user.Platform;
import hr.fer.dsd.privtap.utils.mappers.PlatformMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.time.Instant;
import java.util.*;

@Service
@AllArgsConstructor
public class PlatformService {
    private final PlatformRepository platformRepository;

    public Platform getByName(String name) {
        return PlatformMapper.INSTANCE.fromEntity(platformRepository.findByName(name).orElseThrow(NoSuchElementException::new));
    }

    public void save(PlatformEntity platformEntity){
        platformRepository.save(platformEntity);
    }

    public Platform update(Platform platform) {
        var entity = platformRepository.findByName(platform.getName()).orElseThrow(NoSuchElementException::new);
        var updatedEntity = PlatformMapper.INSTANCE.updateEntity(entity, platform);
        platformRepository.save(updatedEntity);
        return PlatformMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public Platform create(Platform platform) {
        platform.setActions(new ArrayList<ActionType>());
        platform.setTriggers(new ArrayList<TriggerType>());
        platform.setOauthScopes(new ArrayList<String>());

        var entity = PlatformMapper.INSTANCE.toEntity(platform);
        platformRepository.save(entity);
        return PlatformMapper.INSTANCE.fromEntity(entity);
    }

    public List<ActionType> getAllActions(String name) {
        return getByName(name).getActions();
    }

    public List<TriggerType> getAllTriggers(String name) {
        return getByName(name).getTriggers();
    }

    public ActionType getActionType(String name, String id) {
        return getByName(name)
                .getActions()
                .stream()
                .filter(actionType -> actionType.getId().equals(id))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    public TriggerType getTriggerType(String name, String id) {
        return getByName(name)
                .getTriggers()
                .stream()
                .filter(triggerType -> triggerType.getId().equals(id))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    public String getAuthorizationURL(Platform platform) {
        return platform.getOauthUrl() + "?client_id=" + platform.getClientId()
                + "&response_type=code&redirect_uri=" + getRedirectUrl(platform.getName())
                + (platform.getOauthScopes().isEmpty() ? "" : "&scope=" + String.join(",", platform.getOauthScopes()));

    }

    public void getAuthToken(Platform platform, String code) {
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add("grant_type", "authorization_code");
        bodyValues.add("code", code);
        bodyValues.add("redirect_uri", getRedirectUrl(platform.getName()));
        bodyValues.add("client_id", platform.getClientId());
        bodyValues.add("client_secret", platform.getClientSecret());

        try {
            WebClient client = WebClient.builder()
                            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .build();

            client.post()
                .uri(new URI(platform.getOauthTokenUrl()))
                .body(BodyInserters.fromValue(bodyValues))
                    .exchange()
                    .block()
                .toEntity(AuthTokenResponse.class)
                .subscribe(response -> {
                    System.out.println(response.getBody().getAccess_token());
                });
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    private String getRedirectUrl(String platformName) {
        return "http://localhost:8080/platform/" + platformName + "/getCode";
    }

    // TODO: 08.12.2022. add scopes to platform 
    public Platform registerTriggerType(String platformName, TriggerType triggerType) {
        triggerType.setCreatedAt(Instant.now());
        triggerType.setUpdatedAt(Instant.now());
        PlatformEntity entity = platformRepository.findByName(platformName).orElseThrow(NoSuchElementException::new);
        Platform platform = PlatformMapper.INSTANCE.fromEntity(entity);

        List<TriggerType> triggerTypes = platform.getTriggers();
        triggerTypes.add(triggerType);
        platform.setTriggers(triggerTypes);

        return update(platform);
    }

    // TODO: 08.12.2022. add scopes to platform
    public Platform registerActionType(String platformName, ActionType actionType) {
        actionType.setCreatedAt(Instant.now());
        actionType.setUpdatedAt(Instant.now());
        PlatformEntity entity = platformRepository.findByName(platformName).orElseThrow(NoSuchElementException::new);
        Platform platform = PlatformMapper.INSTANCE.fromEntity(entity);

        List<ActionType> actionTypes = platform.getActions();
        actionTypes.add(actionType);
        platform.setActions(actionTypes);

        return update(platform);
    }

    public List<String> getOAuthScopes(String platformName) {
        PlatformEntity entity = platformRepository.findByName(platformName).orElseThrow(NoSuchElementException::new);
        Platform platform = PlatformMapper.INSTANCE.fromEntity(entity);

        return platform.getOauthScopes();
    }

    // TODO: 08.12.2022. remove this
    ActionService actionService;

    public void callAction() {
        Action action = actionService.createFromType(getAllActions("spotify").get(0), "");
        actionService.handler(action);
    }
}