package hr.fer.dsd.privtap.domain.repositories;

import hr.fer.dsd.privtap.domain.entities.TriggerTypeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TriggerTypeRepository extends MongoRepository<TriggerTypeEntity, String> {

    List<TriggerTypeEntity> findByPlatform(String platform);
}
