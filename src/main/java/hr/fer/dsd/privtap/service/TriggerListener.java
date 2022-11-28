package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import hr.fer.dsd.privtap.model.trigger.TriggerEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        var automations = userService.getAllAutomations(userId);
        automations = automations
                .stream()
                .filter(a -> a
                        .getTrigger()
                        .getTypeId()
                        .equals(event.getTrigger().getTypeId()))
                .collect(Collectors.toSet());
        for(var automation : automations){
            var action = automation.getAction();
            var actionFields = action.getFields();
            var triggerFields = event.getTrigger().getFields();
            for (RequestField actionField : actionFields) {
                var triggerField = triggerFields
                        .stream()
                        .filter(requestField -> requestField.getName().equals(actionField.getName()))
                        .findAny().get();
                var triggerFieldValue = triggerField.getValue();
                actionField.setValue(triggerFieldValue);
            }
            actionService.handler(action);
        }
    }
}
