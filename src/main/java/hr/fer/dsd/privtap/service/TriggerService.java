package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.repositories.TriggerRepository;
import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.trigger.Trigger;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.utils.mappers.TriggerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
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

    public Trigger update(String triggerId, Trigger trigger) {
        trigger.setId(triggerId);
        var entity = triggerRepository.findById(trigger.getId()).orElseThrow(NoSuchElementException::new);
        var updatedEntity = TriggerMapper.INSTANCE.updateEntity(entity, trigger);
        updatedEntity.setUpdatedAt(Instant.now());

        triggerRepository.save(updatedEntity);
        return TriggerMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public void delete(String triggerId) {
        var entity = triggerRepository.findById(triggerId).orElseThrow(NoSuchElementException::new);
        triggerRepository.delete(entity);
    }

    public Trigger findById(String id) {
        return TriggerMapper.INSTANCE.fromEntity(triggerRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public List<Trigger> getAll() {
        return triggerRepository.findAll().stream().map(TriggerMapper.INSTANCE::fromEntity).toList();
    }

    public List<Trigger> filterByType(String typeId) {
        return triggerRepository.findByTypeId(typeId).stream().map(TriggerMapper.INSTANCE::fromEntity).toList();
    }

    public List<String> findUserIds(String typeId) {
        return filterByType(typeId).stream().map(Trigger::getUserId).collect(Collectors.toList());
    }

    public Trigger getByTypeAndUser(String triggerType, String userId) {
        return TriggerMapper.INSTANCE.fromEntity(
                triggerRepository.findByTypeIdAndUserId(triggerType,userId).orElseThrow(NoSuchElementException::new));
    }

    public boolean existsByTypeIdAndUserId(String typeId, String userId) {
        return triggerRepository.findByTypeIdAndUserId(typeId,userId).isPresent();
    }

    public Trigger createFromType(TriggerType triggerType, String userId) {
        var fieldsList = new ArrayList<RequestField>();
        for(var fieldName : triggerType.getRequestFieldsNames()){
            var field = ((RequestField)fieldName.getRelatedClass()).buildDefault(fieldName);
            fieldsList.add( field);
        }
        Trigger trigger = Trigger.builder()
                .userId(userId)
                .name(triggerType.getName())
                .typeId(triggerType.getId())
                .description(triggerType.getDescription())
                .fields(fieldsList).build();

        return activateTrigger(trigger);
    }
}
