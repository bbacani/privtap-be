package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.repositories.UserRepository;
import hr.fer.dsd.privtap.model.automation.Automation;
import hr.fer.dsd.privtap.model.automation.AutomationRequest;
import hr.fer.dsd.privtap.model.user.User;
import hr.fer.dsd.privtap.utils.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final ActionTypeService actionTypeService;
    private final TriggerTypeService triggerTypeService;

    public void registerUser(User user) {
        if (!repository.existsByEmail(user.getEmail()))
            create(user);
    }

    public User update(User user) {
        var entity = repository.findById(user.getId()).orElseThrow(NoSuchElementException::new);
        var updatedEntity = UserMapper.INSTANCE.updateEntity(entity, user);

        repository.save(updatedEntity);
        return UserMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public User getById(String id) {
        return UserMapper.INSTANCE.fromEntity(repository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public List<User> getAllUsers() {
        return repository.findAll().stream().map(UserMapper.INSTANCE::fromEntity).toList();
    }

    private void create(User user) {
        var entity = UserMapper.INSTANCE.toEntity(user);
        entity.setAutomations(new HashSet<>());
        repository.save(entity);
    }

    public User registerAutomation(String userId, AutomationRequest request) {
        var action = actionTypeService.get(request.getActionTypeId());
        var trigger = triggerTypeService.get(request.getTriggerTypeId());
        var user = getById(userId);

        var automation = Automation.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .actionType(action)
                .triggerType(trigger)
                .build();

        user.getAutomations().add(automation);
        var entity = UserMapper.INSTANCE.toEntity(user);
        repository.save(entity);
        return user;
    }

    public Set<Automation> getAutomationByUser(String userId){
        var user = getById(userId);
        return user.getAutomations();
    }
}