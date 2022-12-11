package hr.fer.dsd.privtap.model.trigger;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trigger {
    private String id;
    private String userId;
    private String platformName;
    private String name;
    private String typeId;
    private String description;
    private List<RequestField> fields;
    private Set<String> oauthScopes;
    private Instant createdAt;
    private Instant updatedAt;
}
