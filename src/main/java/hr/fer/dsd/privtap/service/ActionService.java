package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.repositories.ActionRepository;
import hr.fer.dsd.privtap.domain.repositories.ActionTypeRepository;
import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.rest.ActionCaller;
import hr.fer.dsd.privtap.utils.mappers.ActionMapper;
import hr.fer.dsd.privtap.utils.mappers.ActionTypeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ActionService {

    private final ActionRepository actionRepository;
    private final ActionTypeRepository actionTypeRepository;

    public Action create(Action action) {
        var entity = ActionMapper.INSTANCE.toEntity(action);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        var savedEntity = actionRepository.save(entity);
        return ActionMapper.INSTANCE.fromEntity(savedEntity);
    }

    public Action update(String actionId, Action action) {
        action.setId(actionId);
        var entity = actionRepository.findById(action.getId()).orElseThrow(NoSuchElementException::new);
        var updatedEntity = ActionMapper.INSTANCE.updateEntity(entity, action);
        updatedEntity.setUpdatedAt(Instant.now());

        actionRepository.save(updatedEntity);
        return ActionMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public void delete(String actionId) {
        var entity = actionRepository.findById(actionId).orElseThrow(NoSuchElementException::new);
        actionRepository.delete(entity);
    }

    public Action get(String id) {
        return ActionMapper.INSTANCE.fromEntity(actionRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public List<Action> getAll() {
        return actionRepository.findAll().stream().map(ActionMapper.INSTANCE::fromEntity).toList();
    }

    public Action getByTypeAndUser(String actionType, String userId) {
        return ActionMapper.INSTANCE.fromEntity(
                actionRepository.findByTypeIdAndUserId(actionType,userId).orElseThrow(NoSuchElementException::new));
    }

    public Action createFromType(ActionType actionType,String userId) {
        var fieldsList = new ArrayList<RequestField>();
        for(var fieldName : actionType.getRequestFieldsNames()){
            var field = ((RequestField)fieldName.getRelatedClass()).buildDefault(fieldName);
            fieldsList.add(field);
        }
        Action action = Action.builder()
                .userId(userId)
                .name(actionType.getName())
                .typeId(actionType.getId())
                .description(actionType.getDescription())
                .fields(fieldsList).build();

        return create(action);
    }

    public boolean existsByTypeIdAndUserId(String typeId, String userId) {
        return actionRepository.findByTypeIdAndUserId(typeId,userId).isPresent();
    }

    public void handler(Action action) throws URISyntaxException {
        //temporary
        for(int i = 0; i < action.getFields().size(); i++){
            System.out.println("field name: " + action.getFields().get(i).getName().toString() + " - value: "
                    + action.getFields().get(i).getValue().toString());
        }
        var actionType= ActionTypeMapper.INSTANCE.fromEntity(
                actionTypeRepository.findById(action.getTypeId()).orElseThrow(NoSuchElementException::new));
        String endpoint = actionType.getUrl();
        ActionCaller actionCaller = new ActionCaller();
        //actionCaller.callAction(endpoint, action);

        //actionCaller.secondAttempt(endpoint, action);
        //actionCaller.blockingCall(endpoint, action);
        actionCaller.blockingCall2(endpoint, action);
    }
}
