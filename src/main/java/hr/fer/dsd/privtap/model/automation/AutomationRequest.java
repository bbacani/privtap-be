package hr.fer.dsd.privtap.model.automation;

import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutomationRequest {
    private String name;
    private String description;
    private String actionTypeId;
    private String triggerTypeId;
}
