package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.entities.OAuthCredentialsEntity;
import hr.fer.dsd.privtap.domain.repositories.OAuthCredentialsRepository;
import hr.fer.dsd.privtap.model.auth0.OAuthCredentials;
import hr.fer.dsd.privtap.utils.mappers.OAuthCredentialsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class OAuthCredentialsService {
    private final OAuthCredentialsRepository oauthCredentialsRepository;

    public void save(OAuthCredentialsEntity entity) {
        oauthCredentialsRepository.save(entity);
    }

    public OAuthCredentials update(OAuthCredentials oAuthCredentials) {
        OAuthCredentialsEntity entity = oauthCredentialsRepository.findByUserIdAndPlatformName(oAuthCredentials.getUserId(), oAuthCredentials.getPlatformName()).orElseThrow(NoSuchElementException::new);
        OAuthCredentialsEntity updatedEntity = OAuthCredentialsMapper.INSTANCE.updateEntity(entity, oAuthCredentials);
        oauthCredentialsRepository.save(updatedEntity);
        return OAuthCredentialsMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public boolean existsByUserIdAndPlatformName(String userId, String platformName) {
        return oauthCredentialsRepository.findByUserIdAndPlatformName(userId, platformName).isPresent();
    }

    public OAuthCredentials create(OAuthCredentials credentials) {
        OAuthCredentialsEntity entity = OAuthCredentialsMapper.INSTANCE.toEntity(credentials);
        if (existsByUserIdAndPlatformName(entity.getUserId(), entity.getPlatformName())) {
            update(credentials);
        } else {
            save(entity);
        }
        return OAuthCredentialsMapper.INSTANCE.fromEntity(entity);
    }

    public OAuthCredentials get(String userId, String platformName) {
        OAuthCredentialsEntity entity = oauthCredentialsRepository.findByUserIdAndPlatformName(userId, platformName).orElseThrow(NoSuchElementException::new);
        return OAuthCredentialsMapper.INSTANCE.fromEntity(entity);
    }
}
