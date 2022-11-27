package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.service.ActionTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/actionTypes")
public class ActionTypeController {
    private final ActionTypeService service;

    @PostMapping
    public ActionType registerActionType(@Valid @RequestBody ActionType actionType) {
        return service.create(actionType);
    }

    @PatchMapping("/{actionTypeId}")
    public ActionType updateActionType(@PathVariable @NotNull String actionTypeId, @Valid @RequestBody ActionType actionType) {
        return service.update(actionTypeId, actionType);
    }

    @GetMapping("/{actionTypeId}")
    public ActionType getActionType(@PathVariable @NotNull String actionTypeId) {
        return service.get(actionTypeId);
    }

    @GetMapping
    public List<ActionType> getAllActionTypes() {
        return service.getAll();
    }

    @GetMapping("/platform/{platform}")
    public List<ActionType> getAllByPlatform(@PathVariable @NotNull String platform) {
        return service.getAllByPlatform(platform);
    }

    @GetMapping("/platforms")
    public List<String> getAllPlatforms() {
        return getAllActionTypes().stream().map(ActionType::getPlatform).distinct().collect(Collectors.toList());
    }
}
