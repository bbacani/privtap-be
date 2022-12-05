package hr.fer.dsd.privtap.domain.repositories;

import hr.fer.dsd.privtap.domain.entities.ActionTypeEntity;
import hr.fer.dsd.privtap.domain.entities.PlatformEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PlatformRepository extends MongoRepository<PlatformEntity, String> {
    Optional<PlatformEntity> findByName(String name);

}
