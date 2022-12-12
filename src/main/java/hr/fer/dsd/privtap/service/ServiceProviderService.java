package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.entities.PlatformEntity;
import hr.fer.dsd.privtap.domain.entities.ServiceProviderEntity;
import hr.fer.dsd.privtap.domain.repositories.PlatformRepository;
import hr.fer.dsd.privtap.domain.repositories.ServiceProviderRepository;
import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.model.user.Platform;
import hr.fer.dsd.privtap.model.user.ServiceProvider;
import hr.fer.dsd.privtap.utils.mappers.PlatformMapper;
import hr.fer.dsd.privtap.utils.mappers.ServiceProviderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@AllArgsConstructor
public class ServiceProviderService {

    private final ServiceProviderRepository serviceProviderRepository;

    private final PlatformRepository platformRepository;

    private final OAuthCredentialsService oAuthCredentialsService;


    public Platform getPlatform(String providerId){
        String platformId = getById(providerId).getPlatformId();
        return PlatformMapper.INSTANCE.fromEntity(platformRepository.findById(platformId).orElseThrow(NoSuchElementException::new));
    }

    public ServiceProvider getById(String id) {
        return ServiceProviderMapper.INSTANCE.fromEntity(serviceProviderRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public Platform update(Platform platform) {
        PlatformEntity entity = platformRepository.findByName(platform.getName()).orElseThrow(NoSuchElementException::new);
        PlatformEntity updatedEntity = PlatformMapper.INSTANCE.updateEntity(entity, platform);
        platformRepository.save(updatedEntity);
        return PlatformMapper.INSTANCE.fromEntity(updatedEntity);
    }


    public Platform registerPlatform(String providerId, Platform platform) {
        platform.setActionTypes(new ArrayList<ActionType>());
        platform.setTriggerTypes(new ArrayList<TriggerType>());
        platform.setOauthScopes(new HashSet<String>());

        PlatformEntity entity = PlatformMapper.INSTANCE.toEntity(platform);
        entity = platformRepository.save(entity);

        ServiceProviderEntity provider = ServiceProviderMapper.INSTANCE.toEntity(getById(providerId));
        provider.setPlatformId(entity.getId());
        provider = serviceProviderRepository.save(provider);
        return PlatformMapper.INSTANCE.fromEntity(entity);
    }

    public Platform registerTriggerType(String providerId, TriggerType triggerType) {
        triggerType.setCreatedAt(Instant.now());
        triggerType.setUpdatedAt(Instant.now());
        Platform platform = getPlatform(providerId);

        List<TriggerType> triggerTypes = platform.getTriggerTypes();
        triggerTypes.add(triggerType);
        platform.setTriggerTypes(triggerTypes);

        Set<String> oauthScopes = platform.getOauthScopes();
        oauthScopes.addAll(triggerType.getOauthScopes());
        platform.setOauthScopes(oauthScopes);

        return update(platform);
    }

    public Platform registerActionType(String providerId, ActionType actionType) {
        actionType.setCreatedAt(Instant.now());
        actionType.setUpdatedAt(Instant.now());
        Platform platform = getPlatform(providerId);

        List<ActionType> actionTypes = platform.getActionTypes();
        actionTypes.add(actionType);
        platform.setActionTypes(actionTypes);

        Set<String> oauthScopes = platform.getOauthScopes();
        oauthScopes.addAll(actionType.getOauthScopes());
        platform.setOauthScopes(oauthScopes);

        return update(platform);
    }
}
