package hr.fer.dsd.privtap.model.trigger;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trigger {
    private String id;
    @NotNull(message = "userId can not be null")
    private String userId;
    @NotNull(message = "name can not be null")
    private String name;
    @NotNull(message = "typeId can not be null")
    private String typeId;
    private String description;
    private List<RequestField> fields;
    private Instant createdAt;
    private Instant updatedAt;
}
