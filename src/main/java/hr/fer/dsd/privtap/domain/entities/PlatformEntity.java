package hr.fer.dsd.privtap.domain.entities;

import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "platforms")
public class PlatformEntity {

    @Id
    private String id;
    private String username;
    private String email;
    private Set<TriggerType> triggers;
    private Set<ActionType> actions;
}
