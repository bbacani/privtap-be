package hr.fer.dsd.privtap.rest;


import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.service.ActionTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/actionTypes")
public class ActionTypeController {
    private final ActionTypeService service;

    @PostMapping
    public ActionType registerActionType(@RequestBody ActionType actionType) {
        return service.create(actionType);
    }

    @PatchMapping("/{actionTypeId}")
    public ActionType updateActionType(@PathVariable @NotNull String actionTypeId, @RequestBody ActionType actionType) {
        actionType.setId(actionTypeId);
        return service.update(actionType);
    }

    @GetMapping("/{actionTypeId}")
    public ActionType getActionType(@PathVariable @NotNull String actionTypeId) {
        return service.get(actionTypeId);
    }

    @GetMapping
    public List<ActionType> getAllActionTypes() {
        return service.getAll();
    }
}