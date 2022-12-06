package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.entities.PlatformEntity;
import hr.fer.dsd.privtap.domain.repositories.ActionTypeRepository;
import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.user.Platform;
import hr.fer.dsd.privtap.utils.mappers.ActionTypeMapper;
import hr.fer.dsd.privtap.utils.mappers.PlatformMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ActionTypeService {

    private final ActionTypeRepository actionTypeRepository;
    private final PlatformService platformService;

    public ActionType create(ActionType action) {
        var entity = ActionTypeMapper.INSTANCE.toEntity(action);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        var savedEntity = actionTypeRepository.save(entity);
        var platform = new Platform();
        var platformEntity = new PlatformEntity();
        try{
            platform = platformService.getByName(action.getPlatform());
        }catch (NoSuchElementException e){
            //temporary since login is not implemented yet
            //the platform will be for sure already present
            platform = platformService.create(action.getPlatform());
        }
        var actionCreated = ActionTypeMapper.INSTANCE.fromEntity(savedEntity);
        platform.getActions().add(actionCreated);
        platformEntity = PlatformMapper.INSTANCE.toEntity(platform);
        //var updatedEntity = PlatformMapper.INSTANCE.updateEntity(platformEntity, platform);
        platformService.save(platformEntity);
        //platformService.update(platform);
        return actionCreated;
    }

    public ActionType update(String actionTypeId, ActionType actionType) {
        actionType.setId(actionTypeId);
        var entity = actionTypeRepository.findById(actionType.getId()).orElseThrow(NoSuchElementException::new);
        var updatedEntity = ActionTypeMapper.INSTANCE.updateEntity(entity, actionType);
        actionTypeRepository.save(updatedEntity);

        var platform = platformService.getByName(actionType.getPlatform());
        var actionToChange = platform.getActions().stream().filter(actionType1 -> actionType1.getId().equals(actionTypeId)).findAny();
        platform.getActions().remove(actionToChange.get());
        platform.getActions().add(actionType);
        platformService.update(platform);

        return ActionTypeMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public ActionType get(String id){
        return ActionTypeMapper.INSTANCE.fromEntity(actionTypeRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public List<ActionType> getAll() {
        return actionTypeRepository.findAll().stream().map(ActionTypeMapper.INSTANCE::fromEntity).toList();
    }

    public List<ActionType> getAllByPlatform(String platform){
        return actionTypeRepository.findByPlatform(platform).stream().map(ActionTypeMapper.INSTANCE::fromEntity).toList();
    }

}
