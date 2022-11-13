package hr.fer.dsd.privtap.model.requestField;

import lombok.Data;

@Data
public abstract class RequestField {
    private String name;
    private Object value;
}
