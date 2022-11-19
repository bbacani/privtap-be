package hr.fer.dsd.privtap.domain.repositories;

import hr.fer.dsd.privtap.domain.entities.ActionTypeEntity;
import hr.fer.dsd.privtap.domain.entities.TriggerTypeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTypeRepository  extends MongoRepository<ActionTypeEntity, String> {
}
