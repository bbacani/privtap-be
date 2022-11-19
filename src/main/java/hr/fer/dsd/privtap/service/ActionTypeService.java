package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.repositories.ActionTypeRepository;
import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.utils.mappers.ActionTypeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ActionTypeService {

    private final ActionTypeRepository actionTypeRepository;

    public ActionType create(ActionType action) {
        var entity = ActionTypeMapper.INSTANCE.toEntity(action);

        var savedEntity = actionTypeRepository.save(entity);
        return ActionTypeMapper.INSTANCE.fromEntity(savedEntity);
    }

    public ActionType update(ActionType actionType) {
        var entity = actionTypeRepository.findById(actionType.getId()).orElseThrow(NoSuchElementException::new);
        var updatedEntity = ActionTypeMapper.INSTANCE.updateEntity(entity, actionType);
        actionTypeRepository.save(updatedEntity);
        return ActionTypeMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public ActionType get(String id){
        return ActionTypeMapper.INSTANCE.fromEntity(actionTypeRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public List<ActionType> getAll() {
        return actionTypeRepository.findAll().stream().map(ActionTypeMapper.INSTANCE::fromEntity).toList();
    }


}
