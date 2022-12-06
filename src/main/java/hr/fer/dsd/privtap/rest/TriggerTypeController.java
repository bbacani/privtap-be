package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.service.TriggerTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/triggerTypes")
public class TriggerTypeController {
    private final TriggerTypeService service;

    @PostMapping
    public TriggerType registerTriggerType(@RequestBody TriggerType triggerType) {
        return service.create(triggerType);
    }

    @PatchMapping("/{triggerTypeId}")
    public TriggerType updateTriggerType(@PathVariable @NotNull String triggerTypeId, @RequestBody TriggerType triggerType) {
        return service.update(triggerTypeId, triggerType);
    }

    @GetMapping("/{triggerTypeId}")
    public TriggerType getTriggerType(@PathVariable @NotNull String triggerTypeId) {
        return service.get(triggerTypeId);
    }

    @GetMapping
    public List<TriggerType> getAllTriggerTypes() {
        return service.getAll();
    }

    @GetMapping("/platform/{platform}")
    public List<TriggerType> getAllByPlatform(@PathVariable @NotNull String platform) {
        return service.getAllByPlatform(platform);
    }

    @GetMapping("/platforms")
    public List<String> getAllPlatforms(){
        return getAllTriggerTypes().stream().map(TriggerType::getPlatformName).distinct().collect(Collectors.toList());
    }
}
