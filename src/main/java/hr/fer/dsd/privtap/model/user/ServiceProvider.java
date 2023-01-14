package hr.fer.dsd.privtap.model.user;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceProvider {

    @Id
    private String id;
    private String email;
    private String password;
    private String platformId;
}
