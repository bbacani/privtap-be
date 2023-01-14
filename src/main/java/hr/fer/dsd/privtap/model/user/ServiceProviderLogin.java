package hr.fer.dsd.privtap.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceProviderLogin {
    private String email;
    private String password;
}
