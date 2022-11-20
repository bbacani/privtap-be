package hr.fer.dsd.privtap.model.requestField.date;

import hr.fer.dsd.privtap.model.requestField.RequestField;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Date extends RequestField {

    public Date(String value) {
        this.setValue(value);
    }
}
