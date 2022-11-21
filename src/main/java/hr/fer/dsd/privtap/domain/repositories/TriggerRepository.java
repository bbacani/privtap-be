package hr.fer.dsd.privtap.domain.repositories;

import hr.fer.dsd.privtap.domain.entities.TriggerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TriggerRepository extends MongoRepository<TriggerEntity, String> {

    List<TriggerEntity> findByTypeId(String typeId);

    boolean existsByTypeIdAndUserId(String typeId, String userId);
}

