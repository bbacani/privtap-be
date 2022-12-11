package hr.fer.dsd.privtap.rest;

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

    private PlatformService platformService;

    @PostMapping
    public Platform registerPlatform(@RequestBody Platform platform) {
        return platformService.create(platform);
    }

    @GetMapping("/{name}")
    public Platform getByName(@PathVariable @NotNull String name) {
        return platformService.getByName(name);
    }

    @GetMapping("/allActions/{name}")
    public List<ActionType> fetchAllActions(@PathVariable @NotNull String name) {
        return platformService.getAllActions(name);
    }

    @GetMapping("/allTriggers/{name}")
    public List<TriggerType> fetchAllTriggers(@PathVariable @NotNull String name) {
        return platformService.getAllTriggers(name);
    }

    @GetMapping("/{platformName}/getCode")
    public void getCode(@PathVariable @NotNull String platformName, @RequestParam("code") String userCode) {
        Platform platform = platformService.getByName(platformName);
        platformService.getAuthToken(platform, userCode);
    }

    @GetMapping("/{platformName}/authorizationUrl")
    public String getPlatformLogin(@PathVariable @NotNull String platformName) {
        Platform platform = platformService.getByName(platformName);
        return platformService.getAuthorizationURL(platform);
    }

    @PostMapping("/{platformName}/actionType")
    public Platform registerActionType(@PathVariable @NotNull String platformName, @RequestBody ActionType actionType) {
        return platformService.registerActionType(platformName, actionType);
    }

    @PostMapping("/{platformName}/triggerType")
    public Platform registerTriggerType(@PathVariable @NotNull String platformName, @RequestBody TriggerType triggerType) {
        return platformService.registerTriggerType(platformName, triggerType);
    }

    @GetMapping("/{platformName}/oauthScopes")
    public Set<String> getOAuthScopes(@PathVariable @NotNull String platformName) {
        return platformService.getOAuthScopes(platformName);
    }

    // TODO: 08.12.2022. remove this, this is for testing from postman
    @GetMapping("/{platformName}/testAction")
    public void callAction(@PathVariable @NotNull String platformName) {
        platformService.callAction();
    }
}
