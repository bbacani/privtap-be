package hr.fer.dsd.privtap.domain.repositories;

import hr.fer.dsd.privtap.domain.entities.PlatformEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformRepository extends MongoRepository<PlatformEntity, String> {
    Optional<PlatformEntity> findByName(String name);

}
