package hr.fer.dsd.privtap.model.action;

import hr.fer.dsd.privtap.model.auth0.OAuthScope;
import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Action implements Serializable {
    @Id
    private String id;
    @NotNull
    private String userId;
    @NotNull
    private String platformName;
    @NotNull
    private String name;
    @NotNull
    private String typeId;
    private String description;
    private String url;
    private List<RequestField> fields;
    private Set<OAuthScope> oauthScopes;
    private Instant createdAt;
    private Instant updatedAt;
}
