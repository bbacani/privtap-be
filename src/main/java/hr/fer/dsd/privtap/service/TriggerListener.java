package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.model.trigger.TriggerEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TriggerListener {

    private UserService userService;
    private ActionService actionService;


    @EventListener
    public void onApplicationEvent(TriggerEvent event){
        var userId = event.getTrigger().getUserId();
        System.out.println("trigger occured");
        var automations = userService.getAutomationByUser(userId);
        automations = automations
                .stream()
                .filter(a -> a
                        .getTriggerType()
                        .getId()
                        .equals(event.getTrigger().getTypeId()))
                .collect(Collectors.toSet());
        for(var a : automations){
            var action = actionService.getByTypeAndUser(a.getActionType().getId(),userId);
            var sentFields = event.getTrigger().getFields();
            action.setFields(sentFields);
            actionService.handler(action);

            //set actionFields and call a method to send the action
        }
    }
}
