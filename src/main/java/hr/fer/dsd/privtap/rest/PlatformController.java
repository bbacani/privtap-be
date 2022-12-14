package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.auth0.OAuthScope;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.model.user.Platform;
import hr.fer.dsd.privtap.service.PlatformService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/platform")
public class PlatformController {

    private final PlatformService platformService;

    @GetMapping("/{platformName}/oauthScopes/{userId}")
    public Set<OAuthScope> getOAuthScopes(@PathVariable @NotNull String platformName, @PathVariable @NotNull String userId) {
        return platformService.getOAuthScopes(platformName, userId);
    }

    @GetMapping("/{platformName}/oauthScopes")
    public Set<OAuthScope> getOAuthScopes(@PathVariable @NotNull String platformName) {
        return platformService.getOAuthScopes(platformName);
    }
    @GetMapping("/{platformName}/unacceptedOauthScopes/{userId}")
    public Set<OAuthScope> getUnacceptedOAuthScopes(@PathVariable @NotNull String platformName,@PathVariable @NotNull String userId){
        return platformService.getUnacceptedOAuthScopes(platformName,userId);
    }

    @GetMapping("/{platformName}/allActionTypes")
    public List<ActionType> getActionTypesByPlatform(@PathVariable @NotNull String platformName) {
        return platformService.getActionTypesByPlatform(platformName);
    }

    @GetMapping("/{platformName}/allTriggerTypes")
    public List<TriggerType> getTriggerTypesByPlatform(@PathVariable @NotNull String platformName) {
        return platformService.getTriggerTypesByPlatform(platformName);
    }

    @GetMapping("/{platformName}/name")
    public Platform getByName(@PathVariable @NotNull String platformName) {
        return platformService.getByName(platformName);
    }

    @GetMapping("/")
    public List<String> getPlatformNames() {
        return platformService.getPlatformNames();
    }

    @GetMapping("/actionPlatforms")
    public List<String> getAllActionPlatforms() {
        return platformService.getAllActionPlatforms();
    }

    @GetMapping("/triggerPlatforms")
    public List<String> getAllTriggerPlatforms() {
        return platformService.getAllTriggerPlatforms();
    }

    @GetMapping("/{platformName}/oauthToken/{userId}")
    public void getOAuthToken(@PathVariable @NotNull String platformName, @PathVariable @NotNull String userId, @RequestParam("code") String userCode) {
        Platform platform = platformService.getByName(platformName);
        platformService.getAuthToken(platform, userCode, userId);
    }

    @PostMapping("/{platformName}/authorizationUrl")
    public String getPlatformLogin(@PathVariable @NotNull String platformName, @RequestBody List<OAuthScope> scopes) {
        Platform platform = platformService.getByName(platformName);
        return platformService.getAuthorizationURL(platform, scopes);
    }

    @PostMapping("/{platformName}/authorizationUrlNewScopes/{userId}")
    public String getAuthorizationURLNewScopes(@PathVariable @NotNull String platformName, @PathVariable @NotNull String userId, @RequestBody List<OAuthScope> scopes){
        Platform platform = platformService.getByName(platformName);
        return platformService.getAuthorizationURLNewScopes(platform,scopes,userId);
    }
    @PostMapping("/{platformName}/authorizationUrlDelScopes/{userId}")
    public String getAuthorizationURLDelScopes(@PathVariable @NotNull String platformName, @PathVariable @NotNull String userId, @RequestBody List<OAuthScope> scopes){
        Platform platform = platformService.getByName(platformName);
        return platformService.getAuthorizationURLRemoveScopes(platform,scopes,userId);
    }

    @GetMapping("/{platformName}/triggerScopes/{triggerTypeid}")
    public Set<OAuthScope> getScopesByTriggerType(@PathVariable @NotNull String platformName, @PathVariable @NotNull String triggerTypeid){
        return platformService.getTriggerType(platformName,triggerTypeid).getOauthScopes();
    }

    @GetMapping("/{platformName}/actionScopes/{actionTypeid}")
    public Set<OAuthScope> getScopesByActionType(@PathVariable @NotNull String platformName, @PathVariable @NotNull String actionTypeid){
        return platformService.getActionType(platformName,actionTypeid).getOauthScopes();
    }

}
