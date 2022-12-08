package hr.fer.dsd.privtap.model.auth0;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuthCredentials {
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