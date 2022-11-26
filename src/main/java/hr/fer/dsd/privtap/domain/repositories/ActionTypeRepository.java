package hr.fer.dsd.privtap.domain.repositories;

import hr.fer.dsd.privtap.domain.entities.ActionTypeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionTypeRepository  extends MongoRepository<ActionTypeEntity, String> {
    List<ActionTypeEntity> findByPlatform(String platform);
}
