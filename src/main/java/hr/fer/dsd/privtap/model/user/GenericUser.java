package hr.fer.dsd.privtap.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericUser {

    private String id;
    private String username;
    private String email;

}
