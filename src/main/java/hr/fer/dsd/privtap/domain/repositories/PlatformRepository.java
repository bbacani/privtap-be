package hr.fer.dsd.privtap.domain.repositories;

import hr.fer.dsd.privtap.domain.entities.PlatformEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlatformRepository extends MongoRepository<PlatformEntity, String> {
}
