package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.trigger.Trigger;
import hr.fer.dsd.privtap.service.TriggerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/trigger")
public class TriggerController {

    private final TriggerService service;

    @PostMapping
    public Trigger registerTrigger(@RequestBody Trigger trigger) {
        return service.create(trigger);
    }

    @PatchMapping("/{triggerId}")
    public Trigger updateTrigger(@PathVariable @NotNull String triggerId, @RequestBody Trigger trigger) {
        trigger.setId(triggerId);
        return service.update(trigger);
    }

    @GetMapping("/{triggerId}")
    public Trigger getTrigger(@PathVariable @NotNull String triggerId) {
        return service.get(triggerId);
    }

    @GetMapping
    public List<Trigger> getAllTriggers() {
        return service.getAll();
    }
}