package hr.fer.dsd.privtap.model.action;

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
public class ActionType {
    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String platformName;
    private String description;
    private List<RequestField> requestFields;
    private Set<OAuthScope> oauthScopes;
    private String url;
    private Instant createdAt;
    private Instant updatedAt;
}
