package hr.fer.dsd.privtap.domain.entities;

import hr.fer.dsd.privtap.model.requestField.RequestFieldName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "triggerTypes")
public class TriggerTypeEntity {

    @Id
    private String id;
    private String platform;
    private String name;
    private String description;
    private List<RequestFieldName> requestFieldsNames;
    private Instant createdAt;
    private Instant updatedAt;
}
