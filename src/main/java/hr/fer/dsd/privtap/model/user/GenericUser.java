package hr.fer.dsd.privtap.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder (toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class GenericUser {

    private String id;
    private String username;
    private String email;

}
