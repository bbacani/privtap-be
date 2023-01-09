package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.repositories.UserRepository;
import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.automation.Automation;
import hr.fer.dsd.privtap.model.automation.AutomationRequest;
import hr.fer.dsd.privtap.model.trigger.Trigger;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.model.user.User;
import hr.fer.dsd.privtap.utils.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EndUserService {
    private final UserRepository userRepository;

    private final ActionService actionService;

    private final TriggerService triggerService;

    private final PlatformService platformService;

    public User getById(String id) {
        return UserMapper.INSTANCE.fromEntity(userRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper.INSTANCE::fromEntity).toList();
    }

    public User registerAutomation(String userId, AutomationRequest request) {
        ActionType actionType = platformService.getActionType(request.getActionTypePlatformName(), request.getActionTypeId());
        TriggerType triggerType = platformService.getTriggerType(request.getTriggerTypePlatformName(), request.getTriggerTypeId());
        Action action = new Action();
        Trigger trigger = new Trigger();
        User user = getById(userId);
        if(!actionService.existsByTypeIdAndUserId(actionType.getId(), userId))
            action = actionService.createFromType(actionType,userId);
        else action = actionService.getByTypeAndUser(actionType.getId(), userId);
        if(!triggerService.existsByTypeIdAndUserId(triggerType.getId(), userId))
            trigger = triggerService.createFromType(triggerType, userId);
        else trigger = triggerService.getByTypeAndUser(triggerType.getId(), userId);

        var automation = Automation.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .action(action)
                .trigger(trigger)
                .build();
        if (user.getAutomations().contains(automation)) {
            throw new RuntimeException();
        }
        user.getAutomations().add(automation);
        var entity = UserMapper.INSTANCE.toEntity(user);
        userRepository.save(entity);
        return user;
    }

    public void deleteAutomation(String userId, String automationId) {
        User user = getById(userId);
        Automation automation = user.getAutomations().stream().filter(aut ->aut.getId().equals(automationId)).collect(Collectors.toList()).get(0);
        String actionTypeId= automation.getAction().getTypeId();
        String triggerTypeId= automation.getTrigger().getTypeId();
        user.getAutomations().remove(automation);
        var entity = UserMapper.INSTANCE.toEntity(user);
        userRepository.save(entity);
        if(user.getAutomations().stream().filter(aut->aut.getAction().getTypeId().equals(actionTypeId)).collect(Collectors.toSet()).size()==0)
            actionService.delete(automation.getAction().getId());
        if(user.getAutomations().stream().filter(aut->aut.getTrigger().getTypeId().equals(triggerTypeId)).collect(Collectors.toSet()).size()==0)
            triggerService.delete(automation.getTrigger().getId());
    }

    public Set<Automation> getAllAutomations(String userId) {
        var user = getById(userId);
        return user.getAutomations();
    }
}
