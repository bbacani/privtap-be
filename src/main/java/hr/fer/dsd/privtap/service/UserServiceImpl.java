package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.entities.UserEntity;
import hr.fer.dsd.privtap.domain.repositories.UserRepository;
import hr.fer.dsd.privtap.exception.ResourceNotFoundException;
import hr.fer.dsd.privtap.model.action.Action;
import hr.fer.dsd.privtap.model.action.ActionType;
import hr.fer.dsd.privtap.model.automation.Automation;
import hr.fer.dsd.privtap.model.automation.AutomationRequest;
import hr.fer.dsd.privtap.model.trigger.Trigger;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import hr.fer.dsd.privtap.model.user.User;
import hr.fer.dsd.privtap.security.UserPrincipal;
import hr.fer.dsd.privtap.utils.mappers.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ActionService actionService;

    private final TriggerService triggerService;

    private final PlatformService platformService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );

        return UserPrincipal.create(user);
    }

    @Override
    public UserDetails loadUserById(String id) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }

    public User update(User user) {
        var entity = userRepository.findById(user.getId()).orElseThrow(NoSuchElementException::new);
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
        return UserMapper.INSTANCE.fromEntity(userRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper.INSTANCE::fromEntity).toList();
    }

    public User registerAutomation(String userId, AutomationRequest request) {
        ActionType actionType = platformService.getActionType(request.getActionTypePlatformName(), userId);
        TriggerType triggerType = platformService.getTriggerType(request.getTriggerTypePlatformName(), userId);
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

    public void deleteAutomation(String userId, Automation automation) {
        var user = getById(userId);
        user.getAutomations().remove(automation);
        var entity = UserMapper.INSTANCE.toEntity(user);
        userRepository.save(entity);
        actionService.delete(automation.getAction().getId());
        triggerService.delete(automation.getTrigger().getId());
    }

    public Set<Automation> getAllAutomations(String userId) {
        var user = getById(userId);
        return user.getAutomations();
    }
}
