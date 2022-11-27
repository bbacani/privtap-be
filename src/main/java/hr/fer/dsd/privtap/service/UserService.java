package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.repositories.UserRepository;
import hr.fer.dsd.privtap.exceptions.*;
import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.model.automation.Automation;
import hr.fer.dsd.privtap.model.automation.AutomationRequest;
import hr.fer.dsd.privtap.model.trigger.Trigger;
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

    public User update(User user) {
        var entity = userRepository.findById(user.getId()).orElseThrow(
                () -> new NoUserFoundException("User with id " + user.getId() + " does not exist")
        );
        var updatedEntity = UserMapper.INSTANCE.updateEntity(entity, user);

        userRepository.save(updatedEntity);
        return UserMapper.INSTANCE.fromEntity(updatedEntity);
    }

    public void create(User user) {
        var entity = UserMapper.INSTANCE.toEntity(user);
        entity.setAutomations(new HashSet<>());
        userRepository.save(entity);
    }

    public User getById(String id) {
        return UserMapper.INSTANCE.fromEntity(userRepository.findById(id).orElseThrow(
                () -> new NoUserFoundException("User with id " + id + " does not exist")
        ));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper.INSTANCE::fromEntity).toList();
    }

    public User registerAutomation(String userId, AutomationRequest request) {
        var action = new Action();
        var trigger = new Trigger();
        var actionType = actionTypeService.get(request.getActionTypeId());
        var triggerType = triggerTypeService.get(request.getTriggerTypeId());

        if (actionService.existsByTypeIdAndUserId(actionType.getId(), userId)) {
            throw new ActionAlreadyExistsException("Action with id " + actionType.getId() + " already exists");
        } else {
            action = actionService.createFromType(actionType, userId);
        }

        if (triggerService.existsByTypeIdAndUserId(triggerType.getId(), userId)) {
            throw new TriggerAlreadyExistsException("Trigger with id " + triggerType.getId() + " already exists");
        } else {
            trigger = triggerService.createFromType(triggerType, userId);
        }

        var user = getById(userId);
        var automation = Automation.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .action(action)
                .trigger(trigger)
                .build();

        if (user.getAutomations().contains(automation)) {
            throw new AutomationAlreadyExistsException("Automation with id " + automation.getId() + " already exists");
        } else {
            user.getAutomations().add(automation);
            var entity = UserMapper.INSTANCE.toEntity(user);
            userRepository.save(entity);
        }

        return user;
    }

    public void deleteAutomation(String userId, Automation automation) {
        var user = getById(userId);
        if (!user.getAutomations().contains(automation)) {
            throw new NoAutomationFoundException("Automation with id " + automation.getId() + " does not exist");
        } else {
            user.getAutomations().remove(automation);
            var entity = UserMapper.INSTANCE.toEntity(user);
            userRepository.save(entity);
        }

        Trigger trigger = automation.getTrigger();
        if (!actionService.existsByTypeIdAndUserId(trigger.getTypeId(), trigger.getUserId())) {
            throw new NoTriggerFoundException("Trigger with id " + trigger.getId() + " does not exist");
        } else {
            actionService.delete(trigger.getId());
        }

        Action action = automation.getAction();
        if (!actionService.existsByTypeIdAndUserId(action.getTypeId(), action.getUserId())) {
            throw new NoActionFoundException("Action with id " + action.getId() + " does not exist");
        } else {
            actionService.delete(action.getId());
        }
    }

    public Set<Automation> getAllAutomations(String userId) {
        var user = getById(userId);
        return user.getAutomations();
    }

}
