package hr.fer.dsd.privtap.service;


import hr.fer.dsd.privtap.domain.repositories.TriggerTypeRepository;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
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

    public TriggerType create(TriggerType triggerType) {
        System.out.printf("yoooooo\n");
        var fieldsList = triggerType.getRequestFieldsNames();
        var entity = TriggerTypeMapper.INSTANCE.toEntity(triggerType);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        entity.setRequestFieldsNames(fieldsList);
        var savedEntity = repository.save(entity);
        return TriggerTypeMapper.INSTANCE.fromEntity(savedEntity);
    }

    public TriggerType update(String triggerId, TriggerType triggerType) {
        triggerType.setId(triggerId);
        var entity = repository.findById(triggerType.getId()).orElseThrow(NoSuchElementException::new);
        var updatedEntity = TriggerTypeMapper.INSTANCE.updateEntity(entity, triggerType);
        repository.save(updatedEntity);
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
