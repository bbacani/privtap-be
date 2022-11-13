package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.repositories.TriggerRepository;
import hr.fer.dsd.privtap.model.trigger.Trigger;
import hr.fer.dsd.privtap.utils.mappers.TriggerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class TriggerService {

    private final TriggerRepository repository;

    public Trigger create(Trigger trigger) {
        var entity = TriggerMapper.INSTANCE.toEntity(trigger);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        var savedEntity = repository.save(entity);
        return TriggerMapper.INSTANCE.fromEntity(savedEntity);

    }

    public Trigger update(Trigger trigger) {
        var entity = repository.findById(trigger.getId()).orElseThrow(NoSuchElementException::new);
        var updatedEntity = TriggerMapper.INSTANCE.updateEntity(entity, trigger);
        updatedEntity.setUpdatedAt(Instant.now());

        repository.save(updatedEntity);
        return TriggerMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public Trigger get(String id) {
        return TriggerMapper.INSTANCE.fromEntity(repository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public List<Trigger> getAll() {
        return repository.findAll().stream().map(TriggerMapper.INSTANCE::fromEntity).toList();

    }
}




