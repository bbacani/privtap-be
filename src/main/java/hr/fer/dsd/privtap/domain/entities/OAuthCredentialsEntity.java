package hr.fer.dsd.privtap.domain.entities;

import hr.fer.dsd.privtap.model.auth0.OAuthScope;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "oauthCredentials")
public class OAuthCredentialsEntity {
    @Id
    private String id;
    private String userId;
    private String platformName;
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
    private String refreshToken;
    private Set<OAuthScope> oauthScopes;
    private Instant createdAt;
    private Instant updatedAt;
}
