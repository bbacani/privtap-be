package hr.fer.dsd.privtap.domain.entities;

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
@Document(collection = "actionTypes")
public class ActionTypeEntity {
    @Id
    private String id;
    private String platform;
    private String name;
    private String description;
    private List<String> requestFieldsNames;
    private Instant createdAt;
    private Instant updatedAt;
}
