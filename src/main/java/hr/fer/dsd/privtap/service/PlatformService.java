package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.entities.PlatformEntity;
import hr.fer.dsd.privtap.domain.repositories.PlatformRepository;
import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.model.auth0.OAuthCredentials;
import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.auth0.OAuthScope;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlatformService {
    private final PlatformRepository platformRepository;
    private final OAuthCredentialsService oAuthCredentialsService;

    public Platform getByName(String name) {
        return PlatformMapper.INSTANCE.fromEntity(platformRepository.findByName(name).orElseThrow(NoSuchElementException::new));
    }

    public List<ActionType> getActionTypesByPlatform(String name) {
        return getByName(name).getActionTypes();
    }
    public List<TriggerType> getTriggerTypesByPlatform(String name) {
        return getByName(name).getTriggerTypes();
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

    public String getAuthorizationURL(Platform platform, List<OAuthScope> oAuthScopes) {
        return platform.getOauthUrl() + "?client_id=" + platform.getClientId()
                + "&response_type=code&redirect_uri=" + getRedirectUrl(platform.getName())
                + (platform.getOauthScopes().isEmpty() ? "" : "&scope=" + String.join(",", oAuthScopes.stream().map(oAuthScope -> oAuthScope.getName()).toList()));
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
                            .oauthScopes(getScopesFromScopeNames(platform, response.getBody().getScope()))
                            .createdAt(Instant.now())
                            .updatedAt(Instant.now())
                            .build();
                    if(!oAuthCredentialsService.existsByUserIdAndPlatformName(userId,platform.getName()))
                        oAuthCredentialsService.create(credentials);
                    else oAuthCredentialsService.update(credentials);
                });
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    private Set<OAuthScope> getScopesFromScopeNames(Platform platform, String oAuthScopeNamesRaw) {
        List<String> oAuthScopeNames = Arrays.stream(oAuthScopeNamesRaw.split(",")).toList();
        return platform.getOauthScopes().stream().filter(oAuthScope -> oAuthScopeNames.contains(oAuthScope.getName())).collect(Collectors.toSet());
    }

    private String getRedirectUrl(String platformName) {
        return "http://localhost:3000/" + platformName + "/successfulLogin";
//        return "http://localhost:3000/platform/" + platformName + "/authToken";
//        return "http://localhost:8080/platform/" + platformName + "/authToken";
    }

    public Set<OAuthScope> getOAuthScopes(String platformName) {
        PlatformEntity entity = platformRepository.findByName(platformName).orElseThrow(NoSuchElementException::new);
        Platform platform = PlatformMapper.INSTANCE.fromEntity(entity);
        return platform.getOauthScopes();
    }

    public Set<OAuthScope> getOAuthScopes(String platformName, String userId) {
        OAuthCredentials credentials = oAuthCredentialsService.get(userId, platformName);
        return credentials.getOauthScopes();
    }
    public Set<OAuthScope> getUnacceptedOAuthScopes(String platformName, String userId){
        Set<OAuthScope> scopes = getOAuthScopes(platformName).stream().filter(
                scope -> !getOAuthScopes( platformName, userId).stream().anyMatch(s->scope.equals(s)))
                .collect(Collectors.toSet());
        return scopes;
    }

    public List<String> getPlatformNames(){
        return platformRepository.findAll().stream().map(platform -> platform.getName()).toList();
    }

    // TODO: 08.12.2022. remove this, this is just mock for action
    ActionService actionService;

    public void callAction() {
        Action action = actionService.createFromType(getActionTypesByPlatform("spotify").get(0), "1");
        actionService.handler(action);
    }
}