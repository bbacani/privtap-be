package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.trigger.Trigger;
import hr.fer.dsd.privtap.service.TriggerPublisher;
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
    private final TriggerPublisher publisher;
    @PostMapping
    public Trigger registerTrigger(@RequestBody Trigger trigger) {
        return service.activateTrigger(trigger);
    }

    @PatchMapping("/{triggerId}")
    public Trigger updateTrigger(@PathVariable @NotNull String triggerId, @RequestBody Trigger trigger) {
        return service.update(triggerId, trigger);
    }

    @GetMapping("/{triggerId}")
    public Trigger getTrigger(@PathVariable @NotNull String triggerId) {
        return service.get(triggerId);
    }

    @GetMapping
    public List<Trigger> getAllTriggers() {
         return service.getAll();
    }

    @PostMapping("/occurance")
    public void triggerOccurance(@RequestBody Trigger trigger) {
        if(null==trigger.getUserId())
            for( var user : service.findUserIds(trigger.getTypeId())){
                trigger.setUserId(user);
                publisher.publishTrigger(trigger);
            }
        else  publisher.publishTrigger(trigger);
    }
}
