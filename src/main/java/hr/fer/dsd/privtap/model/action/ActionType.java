package hr.fer.dsd.privtap.model.action;

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
public class ActionType {
    private String id;
    private String platform;
    private String name;
    private String description;
    private List<RequestFieldName> requestFieldsNames;
    private Instant createdAt;
    private Instant updatedAt;
}
