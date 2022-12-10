package hr.fer.dsd.privtap.domain.repositories;

import hr.fer.dsd.privtap.domain.entities.ActionEntity;
import hr.fer.dsd.privtap.domain.entities.OAuthCredentialsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthCredentialsRepository extends MongoRepository<OAuthCredentialsEntity, String> {

    Optional<OAuthCredentialsEntity> findByUserIdAndPlatformName(String userId, String platformName);
}