package hr.fer.dsd.privtap.domain.entities;

import hr.fer.dsd.privtap.model.auth0.OAuthCredentials;
import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "actions")
public class ActionEntity {

    @Id
    private String id;
    private String userId;
    private String platformName;
    private String name;
    private String typeId;
    private String description;
    private String url;
    private List<RequestField> fields;
    private List<String> oauthScopes;
    private Instant createdAt;
    private Instant updatedAt;
}
