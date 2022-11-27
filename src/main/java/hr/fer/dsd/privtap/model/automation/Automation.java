package hr.fer.dsd.privtap.model.automation;

import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.model.trigger.Trigger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Automation {
    private String id;
    private String name;
    private String description;
    private Trigger trigger;
    private Action action;
}
