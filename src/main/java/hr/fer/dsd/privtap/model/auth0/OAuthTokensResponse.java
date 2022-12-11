package hr.fer.dsd.privtap.model.auth0;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthTokensResponse {
    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String refresh_token;
    private String scope;
}