package hr.fer.dsd.privtap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class PrivtapApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrivtapApplication.class, args);
    }
}
