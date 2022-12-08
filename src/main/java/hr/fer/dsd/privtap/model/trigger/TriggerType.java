package hr.fer.dsd.privtap.model.trigger;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TriggerType {
    @Id
    private String id;
    private String platformName;
    private String name;
    private String description;
    private List<RequestField> requestFields;
    private List<String> oauthScopes;
    private Instant createdAt;
    private Instant updatedAt;
}
