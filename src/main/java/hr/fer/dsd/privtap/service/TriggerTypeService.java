package hr.fer.dsd.privtap.service;


import hr.fer.dsd.privtap.domain.entities.PlatformEntity;
import hr.fer.dsd.privtap.domain.repositories.TriggerTypeRepository;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.model.user.Platform;
import hr.fer.dsd.privtap.utils.mappers.PlatformMapper;
import hr.fer.dsd.privtap.utils.mappers.TriggerTypeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class TriggerTypeService {

    private final TriggerTypeRepository repository;
    private final PlatformService platformService;

    public TriggerType create(TriggerType triggerType) {
        var fieldsList = triggerType.getRequestFieldsNames();
        var entity = TriggerTypeMapper.INSTANCE.toEntity(triggerType);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        entity.setRequestFieldsNames(fieldsList);
        var savedEntity = repository.save(entity);

        var platform = new Platform();
        var platformEntity = new PlatformEntity();
        try{
            platform = platformService.getByName(triggerType.getPlatform());
        }catch (NoSuchElementException e){
            //temporary since login is not implemented yet
            //the platform will be for sure already present
            platform = platformService.create(triggerType.getPlatform());
        }
        var triggerCreated = TriggerTypeMapper.INSTANCE.fromEntity(savedEntity);
        platform.getTriggers().add(triggerCreated);
        platformEntity = PlatformMapper.INSTANCE.toEntity(platform);
        //var updatedEntity = PlatformMapper.INSTANCE.updateEntity(platformEntity, platform);
        platformService.save(platformEntity);
        //platformService.update(platform);
        return triggerCreated;
    }

    public TriggerType update(String triggerId, TriggerType triggerType) {
        triggerType.setId(triggerId);
        var entity = repository.findById(triggerType.getId()).orElseThrow(NoSuchElementException::new);
        var updatedEntity = TriggerTypeMapper.INSTANCE.updateEntity(entity, triggerType);
        repository.save(updatedEntity);
        var platform = platformService.getByName(triggerType.getPlatform());
        var triggerToChange = platform.getTriggers().stream().filter(triggerType1 -> triggerType1.getId().equals(triggerId)).findAny();
        platform.getTriggers().remove(triggerToChange.get());
        platform.getTriggers().add(triggerType);
        platformService.update(platform);
        return TriggerTypeMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public TriggerType get(String id) {
        return TriggerTypeMapper.INSTANCE.fromEntity(repository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public List<TriggerType> getAll() {
        return repository.findAll().stream().map(TriggerTypeMapper.INSTANCE::fromEntity).toList();
    }

    public List<TriggerType> getAllByPlatform(String platform) {
        return repository.findByPlatform(platform).stream().map(TriggerTypeMapper.INSTANCE::fromEntity).toList();
    }

}
