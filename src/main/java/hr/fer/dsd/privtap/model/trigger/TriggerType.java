package hr.fer.dsd.privtap.model.trigger;

import hr.fer.dsd.privtap.model.auth0.OAuthScope;
import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TriggerType {
    @Id
    private String id;
    @NotNull
    private String platformName;
    @NotNull
    private String name;
    private String description;
    private List<RequestField> requestFields;
    private Set<OAuthScope> oauthScopes;
    private Instant createdAt;
    private Instant updatedAt;
}
