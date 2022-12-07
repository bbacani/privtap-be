package hr.fer.dsd.privtap.model.action;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Action implements Serializable {
    private String id;
    private String userId;
    private String name;
    private String typeId;
    private String description;
    private String url;
    private List<RequestField> fields;
    private List<String> oauthScopes;
    private Instant createdAt;
    private Instant updatedAt;
}


