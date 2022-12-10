package hr.fer.dsd.privtap.domain.entities;

import hr.fer.dsd.privtap.model.requestField.RequestField;
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
@Document(collection = "triggers")
public class TriggerEntity {

    @Id
    private String id;
    private String userId;
    private String name;
    private String typeId;
    private String description;
    private List<RequestField> fields;
    private Instant createdAt;
    private Instant updatedAt;
}
