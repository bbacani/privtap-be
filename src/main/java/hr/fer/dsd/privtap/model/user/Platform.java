package hr.fer.dsd.privtap.model.user;

import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Platform {

    @Id
    private String id;
    private String name;
    private String color;
    private String oauthUrl;
    private String oauthTokenUrl;
    private String clientId;
    private String clientSecret;
    private List<TriggerType> triggerTypes;
    private List<ActionType> actionTypes;
    private Set<String> oauthScopes;
}
