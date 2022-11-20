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

    private final UserRepository userRepository;

    private final ActionTypeService actionTypeService;
    private final TriggerTypeService triggerTypeService;
    private final ActionService actionService;
    private final TriggerService triggerService;

    public void registerUser(User user) {
        if (!userRepository.existsByEmail(user.getEmail()))
            create(user);
    }

    public User update(User user) {
        var entity = userRepository.findById(user.getId()).orElseThrow(NoSuchElementException::new);
        var updatedEntity = UserMapper.INSTANCE.updateEntity(entity, user);

        userRepository.save(updatedEntity);
        return UserMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public User getById(String id) {
        return UserMapper.INSTANCE.fromEntity(userRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper.INSTANCE::fromEntity).toList();
    }

    private void create(User user) {
        var entity = UserMapper.INSTANCE.toEntity(user);
        entity.setAutomations(new HashSet<>());
        userRepository.save(entity);
    }

    public User registerAutomation(String userId, AutomationRequest request) {
        var action = actionTypeService.get(request.getActionTypeId());
        var trigger = triggerTypeService.get(request.getTriggerTypeId());
        var user = getById(userId);

       if(!actionService.existsByTypeIdAndUserId(action.getId(),userId))
            actionService.createFromType(action,userId);
        if(!triggerService.existsByTypeIdAndUserId(trigger.getId(),userId))
            triggerService.createFromType(trigger,userId);

        var automation = Automation.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .actionType(action)
                .triggerType(trigger)
                .build();

        user.getAutomations().add(automation);
        var entity = UserMapper.INSTANCE.toEntity(user);
        userRepository.save(entity);
        return user;
    }

    public Set<Automation> getAutomationByUser(String userId){
        var user = getById(userId);
        return user.getAutomations();
    }
}