package hr.fer.dsd.privtap.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

@Data
@EnableAsync
@Configuration
public class AppConfig {
    private List<String> authorizedRedirectUris = List.of("http://localhost:3000/oauth2/redirect");

    private String tokenSecret = "926D96C90030DD58429D2751AC1BDBBC";

    private long tokenExpirationMsec = 864000000;
}
