package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.repositories.TriggerRepository;
import hr.fer.dsd.privtap.domain.repositories.TriggerTypeRepository;
import hr.fer.dsd.privtap.model.trigger.Trigger;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.utils.mappers.TriggerMapper;
import hr.fer.dsd.privtap.utils.mappers.TriggerTypeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TriggerService {

    private final TriggerRepository triggerRepository;

    public Trigger activateTrigger(Trigger trigger) {
        var entity = TriggerMapper.INSTANCE.toEntity(trigger);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        var savedEntity = triggerRepository.save(entity);
        return TriggerMapper.INSTANCE.fromEntity(savedEntity);

    }

    public Trigger update(Trigger trigger) {
        var entity = triggerRepository.findById(trigger.getId()).orElseThrow(NoSuchElementException::new);
        var updatedEntity = TriggerMapper.INSTANCE.updateEntity(entity, trigger);
        updatedEntity.setUpdatedAt(Instant.now());

        triggerRepository.save(updatedEntity);
        return TriggerMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public Trigger get(String id) {
        return TriggerMapper.INSTANCE.fromEntity(triggerRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public List<Trigger> getAll() {
        return triggerRepository.findAll().stream().map(TriggerMapper.INSTANCE::fromEntity).toList();

    }
    public List<Trigger> filterByType(String typeId) {
        return triggerRepository.findByTypeId(typeId).stream().map(TriggerMapper.INSTANCE::fromEntity).toList();
    }
    public List<String> findUserIds(String typeId){
        return filterByType(typeId).stream().map(t -> t.getUserId()).collect(Collectors.toList());
    }

}




