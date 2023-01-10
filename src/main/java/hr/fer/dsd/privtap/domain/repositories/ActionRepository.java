package hr.fer.dsd.privtap.domain.repositories;

import hr.fer.dsd.privtap.domain.entities.ActionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActionRepository extends MongoRepository<ActionEntity, String> {

    List<ActionEntity> findByTypeId(String typeId);

    Optional<ActionEntity> findByTypeIdAndUserId(String typeId, String userId);
}
