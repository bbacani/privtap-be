package hr.fer.dsd.privtap.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.fer.dsd.privtap.model.automation.Automation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String username;
    private String email;
    private Boolean emailVerified = false;
    private String imageUrl;
    @JsonIgnore
    private String password = null;
    private AuthProvider provider;
    private String providerId;
    private Set<Automation> automations;
}
