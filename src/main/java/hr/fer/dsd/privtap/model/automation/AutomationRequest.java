package hr.fer.dsd.privtap.model.automation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutomationRequest {
    @NotNull
    private String name;
    private String description;
    @NotNull
    private String triggerTypePlatformName;
    @NotNull
    private String actionTypePlatformName;
    @NotNull
    private String actionTypeId;
    @NotNull
    private String triggerTypeId;
}
