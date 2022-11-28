package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.service.ActionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/action")
public class ActionController {

    private final ActionService service;

    @PostMapping
    public Action registerAction(@RequestBody Action action) {
        return service.create(action);
    }

    @PatchMapping("/{actionId}")
    public Action updateAction(@PathVariable String actionId, @RequestBody Action action) {
        return service.update(actionId, action);
    }

    @GetMapping("/{actionId}")
    public Action getAction(@PathVariable @NotNull String actionId) {
        return service.get(actionId);
    }

    @GetMapping
    public List<Action> getAllActions() {
        return service.getAll();
    }
}
