package hr.fer.dsd.privtap.model.trigger;

import org.springframework.context.ApplicationEvent;

public class TriggerEvent extends ApplicationEvent {
    private Trigger trigger;

    public TriggerEvent(Object source,Trigger trigger) {
        super(source);
        this.trigger = trigger;
    }

    public Trigger getTrigger() {
        return trigger;
    }
}