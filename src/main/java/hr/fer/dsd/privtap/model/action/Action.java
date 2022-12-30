package hr.fer.dsd.privtap.model.action;

import hr.fer.dsd.privtap.model.auth0.OAuthScope;
import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Action implements Serializable {
    private String id;
    private String userId;
    private String platformName;
    private String name;
    private String typeId;
    private String description;
    private String url;
    private List<RequestField> fields;
    private Set<OAuthScope> oauthScopes;
    private Instant createdAt;
    private Instant updatedAt;
}


