package hr.fer.dsd.privtap.model.action;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionType {
    @Id
    private String id;
    private String name;
    private String platformName;
    private String description;
    private List<RequestField> requestFields;
    private Set<String> oauthScopes;
    private String url;
    private Instant createdAt;
    private Instant updatedAt;
}
