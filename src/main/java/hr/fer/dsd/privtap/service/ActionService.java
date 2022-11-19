package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.repositories.ActionRepository;
import hr.fer.dsd.privtap.domain.repositories.ActionTypeRepository;
import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.utils.mappers.ActionMapper;
import hr.fer.dsd.privtap.utils.mappers.ActionTypeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ActionService {

    private final ActionRepository actionRepository;

    public Action create(Action action) {
        var entity = ActionMapper.INSTANCE.toEntity(action);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        var savedEntity = actionRepository.save(entity);
        return ActionMapper.INSTANCE.fromEntity(savedEntity);
    }

    public Action update(Action action) {
        var entity = actionRepository.findById(action.getId()).orElseThrow(NoSuchElementException::new);
        var updatedEntity = ActionMapper.INSTANCE.updateEntity(entity, action);
        updatedEntity.setUpdatedAt(Instant.now());

        actionRepository.save(updatedEntity);
        return ActionMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public Action get(String id) {
        return ActionMapper.INSTANCE.fromEntity(actionRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public List<Action> getAll() {
        return actionRepository.findAll().stream().map(ActionMapper.INSTANCE::fromEntity).toList();
    }

    public Action getByTypeAndUser(String actionType, String userId){
        return ActionMapper.INSTANCE.fromEntity(actionRepository.findByTypeIdAndUserId(actionType,userId).orElseThrow(NoSuchElementException::new));
    }


}
