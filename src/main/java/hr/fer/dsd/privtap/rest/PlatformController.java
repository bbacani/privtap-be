package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.OAuthScope;
import hr.fer.dsd.privtap.model.action.ActionType;
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

    @GetMapping("/{platformName}/oauthScopes")
    public Set<String> getOAuthScopes(@PathVariable @NotNull String platformName) {
        return platformService.getOAuthScopes(platformName);
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
    public List<String> getPlatformNames(){
        return platformService.getPlatformNames();
    }

    @GetMapping("/triggerPlatforms")
    public List<String> getAllTriggerPlatforms() {
        return platformService.getAllTriggerPlatforms();
    }

    @GetMapping("/actionPlatforms")
    public List<String> getAllActionPlatforms() {
        return platformService.getAllActionPlatforms();
    }

    @GetMapping("/{platformName}/oauthToken/{userId}")
    public void getOAuthToken(@PathVariable @NotNull String platformName, @PathVariable @NotNull String userId, @RequestParam("code") String userCode) {
        Platform platform = platformService.getByName(platformName);
        platformService.getAuthToken(platform, userCode, userId);
    }

    @GetMapping("/{platformName}/authorizationUrl")
    public String getPlatformLogin(@PathVariable @NotNull String platformName, @RequestParam @NotNull List<String> scopes) {
        Platform platform = platformService.getByName(platformName);
        return platformService.getAuthorizationURL(platform, scopes);
    }
}
