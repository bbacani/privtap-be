package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.entities.PlatformEntity;
import hr.fer.dsd.privtap.domain.repositories.PlatformRepository;
import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.model.auth0.OAuthCredentials;
import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.auth0.OAuthTokensResponse;
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
    private final OAuthCredentialsService oAuthCredentialsService;

    public Platform getByName(String name) {
        return PlatformMapper.INSTANCE.fromEntity(platformRepository.findByName(name).orElseThrow(NoSuchElementException::new));
    }

    public List<String> getAllPlatformNames() {
        return platformRepository.findAll().stream().map(platformEntity -> platformEntity.getName()).toList();
    }

    public List<String> getAllTriggerPlatforms() {
        return platformRepository
                .findAll()
                .stream()
                .filter(platformEntity -> !platformEntity.getTriggerTypes().isEmpty())
                .map(platformEntity -> platformEntity.getName())
                .toList();
    }

    public List<String> getAllActionPlatforms() {
        return platformRepository
                .findAll()
                .stream()
                .filter(platformEntity ->
                        !platformEntity.getActionTypes().isEmpty()
                )
                .map(platformEntity -> platformEntity.getName())
                .toList();
    }

    public void save(PlatformEntity platformEntity){
        platformRepository.save(platformEntity);
    }

    public Platform update(Platform platform) {
        PlatformEntity entity = platformRepository.findByName(platform.getName()).orElseThrow(NoSuchElementException::new);
        PlatformEntity updatedEntity = PlatformMapper.INSTANCE.updateEntity(entity, platform);
        platformRepository.save(updatedEntity);
        return PlatformMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public Platform create(Platform platform) {
        platform.setActionTypes(new ArrayList<ActionType>());
        platform.setTriggerTypes(new ArrayList<TriggerType>());
        platform.setOauthScopes(new HashSet<String>());

        PlatformEntity entity = PlatformMapper.INSTANCE.toEntity(platform);
        save(entity);
        return PlatformMapper.INSTANCE.fromEntity(entity);
    }

    public List<ActionType> getActionTypesByPlatform(String platformName) {
        return getByName(platformName).getActionTypes();
    }

    public List<TriggerType> getTriggerTypesByPlatform(String platformName) {
        return getByName(platformName).getTriggerTypes();
    }

    public ActionType getActionType(String platformName, String id) {
        return getByName(platformName)
                .getActionTypes()
                .stream()
                .filter(actionType -> actionType.getId().equals(id))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    public TriggerType getTriggerType(String platformName, String id) {
        return getByName(platformName)
                .getTriggerTypes()
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

    public void getAuthToken(Platform platform, String code, String userId) {
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
                .toEntity(OAuthTokensResponse.class)
                .subscribe(response -> {
                    OAuthCredentials credentials = OAuthCredentials.builder()
                            .userId(userId)
                            .platformName(platform.getName())
                            .accessToken(response.getBody().getAccess_token())
                            .refreshToken(response.getBody().getRefresh_token())
                            .tokenType(response.getBody().getToken_type())
                            .expiresIn(response.getBody().getExpires_in())
                            .scope(response.getBody().getScope())
                            .createdAt(Instant.now())
                            .updatedAt(Instant.now())
                            .build();
                    oAuthCredentialsService.create(credentials);
                });
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    private String getRedirectUrl(String platformName) {
        return "http://localhost:8080/platform/" + platformName + "/getCode";
    }

    public Platform registerTriggerType(String platformName, TriggerType triggerType) {
        triggerType.setCreatedAt(Instant.now());
        triggerType.setUpdatedAt(Instant.now());
        PlatformEntity entity = platformRepository.findByName(platformName).orElseThrow(NoSuchElementException::new);
        Platform platform = PlatformMapper.INSTANCE.fromEntity(entity);

        List<TriggerType> triggerTypes = platform.getTriggerTypes();
        triggerTypes.add(triggerType);
        platform.setTriggerTypes(triggerTypes);

        Set<String> oauthScopes = platform.getOauthScopes();
        oauthScopes.addAll(triggerType.getOauthScopes());
        platform.setOauthScopes(oauthScopes);

        return update(platform);
    }

    public Platform registerActionType(String platformName, ActionType actionType) {
        actionType.setCreatedAt(Instant.now());
        actionType.setUpdatedAt(Instant.now());
        PlatformEntity entity = platformRepository.findByName(platformName).orElseThrow(NoSuchElementException::new);
        Platform platform = PlatformMapper.INSTANCE.fromEntity(entity);

        List<ActionType> actionTypes = platform.getActionTypes();
        actionTypes.add(actionType);
        platform.setActionTypes(actionTypes);

        Set<String> oauthScopes = platform.getOauthScopes();
        oauthScopes.addAll(actionType.getOauthScopes());
        platform.setOauthScopes(oauthScopes);

        return update(platform);
    }

    public Set<String> getOAuthScopes(String platformName) {
        PlatformEntity entity = platformRepository.findByName(platformName).orElseThrow(NoSuchElementException::new);
        Platform platform = PlatformMapper.INSTANCE.fromEntity(entity);

        return platform.getOauthScopes();
    }

    // TODO: 08.12.2022. remove this, this is just mock for action
    ActionService actionService;

    public void callAction() {
        Action action = actionService.createFromType(getActionTypesByPlatform("spotify").get(0), "1");
        actionService.handler(action);
    }
}