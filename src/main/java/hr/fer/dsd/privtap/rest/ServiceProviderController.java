package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.model.user.Platform;
import hr.fer.dsd.privtap.model.user.ServiceProvider;
import hr.fer.dsd.privtap.model.user.ServiceProviderLogin;
import hr.fer.dsd.privtap.service.PlatformService;
import hr.fer.dsd.privtap.service.ServiceProviderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
@AllArgsConstructor
@RestController
@RequestMapping("/serviceProvider")
public class ServiceProviderController {

    private final PlatformService platformService;

    private final ServiceProviderService serviceProviderService;


    @PostMapping("/platform/{serviceProviderId}")
    public Platform registerPlatform(@PathVariable @NotNull String serviceProviderId, @RequestBody Platform platform) {
        return serviceProviderService.registerPlatform(serviceProviderId, platform);
    }

    @GetMapping("/platform/{serviceProviderId}")
    public Platform getPlatform(@PathVariable @NotNull String serviceProviderId){
        return serviceProviderService.getPlatform(serviceProviderId);
    }

    @PostMapping("/{platformName}/actionType")
    public Platform registerActionType(@PathVariable @NotNull String platformName, @RequestBody ActionType actionType) {
        return serviceProviderService.registerActionType(platformName, actionType);
    }

    @PostMapping("/{platformName}/triggerType")
    public Platform registerTriggerType(@PathVariable @NotNull String platformName, @RequestBody TriggerType triggerType) {
        return serviceProviderService.registerTriggerType(platformName, triggerType);
    }

    // TODO: 08.12.2022. remove this, this is for testing from postman
    @GetMapping("/{platformName}/testAction")
    public void callAction(@PathVariable @NotNull String platformName) {
        platformService.callAction();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginServiceProvider(@RequestBody @NotNull ServiceProviderLogin loginData) {
        return serviceProviderService.login(loginData);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerServiceProvider(@RequestBody ServiceProviderLogin registerData) {
        return serviceProviderService.registerServiceProvider(registerData);
    }
}
