package hr.fer.dsd.privtap.domain.repositories;

import hr.fer.dsd.privtap.domain.entities.TriggerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TriggerRepository extends MongoRepository<TriggerEntity, String> {

    List<TriggerEntity> findByTypeId(String typeId);

    Optional<TriggerEntity> findByTypeIdAndUserId(String typeId, String userId);
}

