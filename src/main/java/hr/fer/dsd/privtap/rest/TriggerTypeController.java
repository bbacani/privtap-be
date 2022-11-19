package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.service.TriggerTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

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
    public TriggerType updateTriggerType(@PathVariable @NotNull String triggerId, @RequestBody TriggerType triggerType) {
        triggerType.setId(triggerId);
        return service.update(triggerType);
    }

    @GetMapping("/{triggerTypeId}")
    public TriggerType getTriggerType(@PathVariable @NotNull String triggerId) {
        return service.get(triggerId);
    }

    @GetMapping
    public List<TriggerType> getAllTriggerTypes() {
        return service.getAll();
    }
}
