package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.entities.PlatformEntity;
import hr.fer.dsd.privtap.domain.repositories.PlatformRepository;
import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.model.user.Platform;
import hr.fer.dsd.privtap.utils.mappers.PlatformMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class PlatformService {
    private final PlatformRepository platformRepository;

    public Platform getByName(String name) {

        return PlatformMapper.INSTANCE.fromEntity(platformRepository.findByName(name).orElseThrow(NoSuchElementException::new));
    }

    public void save(PlatformEntity platformEntity){
        //var platformEntity = PlatformMapper.INSTANCE.toEntity(platform);

        platformRepository.save(platformEntity);
    }

    public Platform update(Platform platform) {
        var entity = platformRepository.findByName(platform.getName()).orElseThrow(NoSuchElementException::new);
        var updatedEntity = PlatformMapper.INSTANCE.updateEntity(entity, platform);
        platformRepository.save(updatedEntity);
        return PlatformMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public void create(Platform platform) {
        var entity = PlatformMapper.INSTANCE.toEntity(platform);
        platformRepository.save(entity);
    }

    public List<ActionType> getAllActions(String name) {
        return getByName(name).getActions();
    }

    public List<TriggerType> getAllTriggers(String name) {
        return getByName(name).getTriggers();
    }
}