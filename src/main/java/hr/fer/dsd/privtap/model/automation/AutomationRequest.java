package hr.fer.dsd.privtap.model.automation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutomationRequest {
    private String name;
    private String description;
    private String actionId;
    private String triggerId;
}
