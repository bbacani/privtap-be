package hr.fer.dsd.privtap.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;
    private String username;
    private String email;
    private String password;
}
