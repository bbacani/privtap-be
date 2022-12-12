package hr.fer.dsd.privtap.domain.repositories;

import hr.fer.dsd.privtap.domain.entities.ServiceProviderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceProviderRepository extends MongoRepository<ServiceProviderEntity, String> {

    Optional<ServiceProviderEntity> findByEmail(String email);
}
