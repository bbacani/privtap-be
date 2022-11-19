package hr.fer.dsd.privtap.model.action;

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
    private String name;
    private String description;
    private List<String> requestFieldsNames;
    private Instant createdAt;
    private Instant updatedAt;
}
