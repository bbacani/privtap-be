package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.model.user.Platform;
import hr.fer.dsd.privtap.service.PlatformService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/platform")
public class PlatformController {

    private PlatformService platformService;

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

}
