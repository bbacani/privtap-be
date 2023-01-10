package hr.fer.dsd.privtap.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder (toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class GenericUser {

    private String id;
    private String username;
    private String email;
    private Boolean emailVerified = false;
    private String imageUrl;
    @JsonIgnore
    private String password = null;
    private AuthProvider provider;
    private String providerId;

}
