package hr.fer.dsd.privtap.model.requestField.date;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Date extends RequestField {
    public Date(String value) {
        this.setName("date");
        this.setValue(value);
    }
}
