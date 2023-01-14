package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.entities.PlatformEntity;
import hr.fer.dsd.privtap.domain.entities.ServiceProviderEntity;
import hr.fer.dsd.privtap.domain.repositories.PlatformRepository;
import hr.fer.dsd.privtap.domain.repositories.ServiceProviderRepository;
import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.auth0.OAuthScope;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.model.user.Platform;
import hr.fer.dsd.privtap.model.user.ServiceProvider;
import hr.fer.dsd.privtap.model.user.ServiceProviderLogin;
import hr.fer.dsd.privtap.utils.mappers.PlatformMapper;
import hr.fer.dsd.privtap.utils.mappers.ServiceProviderMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@AllArgsConstructor
public class ServiceProviderService {

    private final ServiceProviderRepository serviceProviderRepository;

    private final PlatformRepository platformRepository;

    private final OAuthCredentialsService oAuthCredentialsService;


    public Platform getPlatform(String id){
        String platformId = getById(id).getPlatformId();
        return PlatformMapper.INSTANCE.fromEntity(platformRepository.findById(platformId).orElseThrow(NoSuchElementException::new));
    }

    public Platform getPlatformByName(String name){
        return PlatformMapper.INSTANCE.fromEntity(platformRepository.findByName(name).orElseThrow(NoSuchElementException::new));
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


    public Platform registerPlatform(String id, Platform platform) {
        platform.setActionTypes(new ArrayList<ActionType>());
        platform.setTriggerTypes(new ArrayList<TriggerType>());
        platform.setOauthScopes(new HashSet<OAuthScope>());

        PlatformEntity entity = PlatformMapper.INSTANCE.toEntity(platform);
        entity = platformRepository.save(entity);

        ServiceProviderEntity provider = ServiceProviderMapper.INSTANCE.toEntity(getById(id));
        provider.setPlatformId(entity.getId());
        provider = serviceProviderRepository.save(provider);
        return PlatformMapper.INSTANCE.fromEntity(entity);
    }

    public Platform registerTriggerType(String platformName, TriggerType triggerType) {
        triggerType.setCreatedAt(Instant.now());
        triggerType.setUpdatedAt(Instant.now());
        Platform platform = getPlatformByName(platformName);

        List<TriggerType> triggerTypes = platform.getTriggerTypes();
        triggerTypes.add(triggerType);
        platform.setTriggerTypes(triggerTypes);

        Set<OAuthScope> oauthScopes = platform.getOauthScopes();
        oauthScopes.addAll(triggerType.getOauthScopes());
        platform.setOauthScopes(oauthScopes);

        return update(platform);
    }

    public Platform registerActionType(String platformName, ActionType actionType) {
        actionType.setCreatedAt(Instant.now());
        actionType.setUpdatedAt(Instant.now());
        Platform platform = getPlatformByName(platformName);

        List<ActionType> actionTypes = platform.getActionTypes();
        actionTypes.add(actionType);
        platform.setActionTypes(actionTypes);

        Set<OAuthScope> oauthScopes = platform.getOauthScopes();
        oauthScopes.addAll(actionType.getOauthScopes());
        platform.setOauthScopes(oauthScopes);

        return update(platform);
    }

    public ResponseEntity<?> login(ServiceProviderLogin loginData) {
        Optional<ServiceProviderEntity> entity = serviceProviderRepository.findByEmail(loginData.getEmail());
        if (entity == null || entity.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else if (!loginData.getPassword().equals(entity.get().getPassword())) {
            return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
        } else {
            ServiceProvider provider = ServiceProviderMapper.INSTANCE.fromEntity(entity.get());
            return new ResponseEntity<>(provider, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> registerServiceProvider(ServiceProviderLogin registerData) {
        if (serviceProviderRepository.findByEmail(registerData.getEmail()) == null) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        ServiceProvider provider = ServiceProvider.builder()
                .email(registerData.getEmail())
                .password(registerData.getPassword())
                .platformId(null)
                .build();
        ServiceProviderEntity savedEntity = serviceProviderRepository.save(ServiceProviderMapper.INSTANCE.toEntity(provider));
        return new ResponseEntity<>(savedEntity, HttpStatus.OK);
    }
}
