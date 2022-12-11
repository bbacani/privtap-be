package hr.fer.dsd.privtap.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "oauthCredentials")
public class OAuthCredentialsEntity {
    private String userId;
    private String platformName;
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
    private String refreshToken;
    private String scope;
    private Instant createdAt;
    private Instant updatedAt;
}
