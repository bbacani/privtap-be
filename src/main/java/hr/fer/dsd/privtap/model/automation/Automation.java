package hr.fer.dsd.privtap.model.automation;

import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.model.trigger.Trigger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Automation {
    @Id
    private String id;
    @NotNull(message = "name can not be null")
    private String name;
    private String description;
    @NotNull(message = "trigger can not be null")
    private Trigger trigger;
    @NotNull(message = "action can not be null")
    private Action action;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Automation that = (Automation) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(trigger, that.trigger) && Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, trigger, action);
    }
}
