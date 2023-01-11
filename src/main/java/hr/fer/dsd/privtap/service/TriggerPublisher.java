package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.model.trigger.Trigger;
import hr.fer.dsd.privtap.model.trigger.TriggerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class TriggerPublisher {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void publishTrigger(Trigger trigger){
        applicationEventPublisher.publishEvent(new TriggerEvent(this, trigger));
    }
}
