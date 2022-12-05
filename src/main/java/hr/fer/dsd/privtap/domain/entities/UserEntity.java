package hr.fer.dsd.privtap.domain.entities;

import hr.fer.dsd.privtap.model.automation.Automation;
import hr.fer.dsd.privtap.model.user.PrivacyPreference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;
    private String username;
    private String email;
    private Set<Automation> automations;
    private List<PrivacyPreference> fieldsAuthorization;

}
