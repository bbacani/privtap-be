package hr.fer.dsd.privtap.model.user;

import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder (toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Platform extends GenericUser {
    private String name;
    private String color;
    private List<TriggerType> triggers;
    private List<ActionType> actions;

}
