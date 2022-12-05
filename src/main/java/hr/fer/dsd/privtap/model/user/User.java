package hr.fer.dsd.privtap.model.user;

import hr.fer.dsd.privtap.model.automation.Automation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends GenericUser {
    private Set<Automation> automations;
}
