package hr.fer.dsd.privtap.domain.entities;

import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
// TODO: 06.12.2022. revert this to "platforms"
@Document(collection = "newPlatforms")
public class PlatformEntity {

    @Id
    private String id;
    private String username;
    private String email;
    private String name;
    private String color;
    private String oauthUrl;
    private List<TriggerType> triggers;
    private List<ActionType> actions;
    private List<String> oauthScopes;
}