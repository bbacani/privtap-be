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
                        .getId()
                        .equals(event.getTrigger().getTypeId()))
                .collect(Collectors.toSet());
        for(var a : automations){
            var action = actionService.getByTypeAndUser(a.getAction().getId(),userId);
            //action.setFields();
            //set actionFields and call a method to send the action
            var action = actionService.getByTypeAndUser(a.getActionType().getId(),userId);
            var fields = new ArrayList<RequestField>();
            for(RequestField f : action.getFields()){
                var eachField = ((RequestField)f.getName().getRelatedClass()).buildDefault(f.getName());
                for(RequestField triggerField : event.getTrigger().getFields()){
                    if (triggerField.getName().equals(f.getName())){
                        eachField.setValue(triggerField.getValue());
                    }
                }
                fields.add(eachField);
            }
            action.setFields(fields);
            actionService.handler(action);
        }
    }
}
