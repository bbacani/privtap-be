package hr.fer.dsd.privtap.model.trigger;

import hr.fer.dsd.privtap.model.requestField.RequestFieldName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TriggerType {
    private String id;
    private String platformName;
    private String name;
    private String description;
    private List<RequestFieldName> requestFieldsNames;
    private Instant createdAt;
    private Instant updatedAt;
}
